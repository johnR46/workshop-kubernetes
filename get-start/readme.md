# get-start
# kubectl command หลักๆ ที่ต้องรู้ไว้
- kubectl get <pod/services> (list resources ทั้งหมด)
- kubectl describe <pod/services> (show infromation of resources)
- kubectl scale <deployment name> –replicas=<Number of deployment replica>
- kubectl set <deployment name> <image:version>
- kubectl rollout undo <deployment name>

# Basic Concept 
- การ Deploy มี 2 แบบ 

1. แบบที่ 1 Command line  

   ทดลองใช้คำสั่ง

 > kubectl run hello-kube --image=johnr46/hello-kube:1.0 --port=80 
   จะเป็นการสร้าง Pod(ที่มี container รันงานของเรา) ขึ้นมาหนึ่งตัว
  
 > kubectl expose pod hello-kube-service --type=LoadBalancer --port=80
   จะเป็นการ สร้าง Service ขึ้นมาเพื่อสร้างทางเข้าใช้งานแอปเรา ( Expose Service สำหรับ Access Pod )

เสร็จแล้ว ทดสอบเข้าไปดูที่ http://localhost:80 จะเห็นว่า app เราทำงานอยู่

เสร็จแล้วลบออกด้วย (ถ้าไม่ลบมันจะรกเฉยๆ ดูลำบากตอนจะทำแลปต่อไป)
 > kubectl delete pod hello-kube 
   ลบ pod ที่ชื่อ hello-kube

 > kubectl delete Service hello-kube-service
   ลบ Service ที่ชื่อ hello-kube-service

2. แบบที่ 2 manifest file 
- บอกโครงสร้าง ของ Resource ทั้งหมดที่เราจะสร้าง , อธิบาย  การทำลักษณะนี้เรียกว่า  Infrastructure as Code (Iac)  ควรจะทำแบบนี้  
     (สามารถ Control Infrastructure  ได้ด้วยไฟล์นี้)
   
   ลองใช้คำสั่ง 
 > kubectl apply -f hello-kube-pod.yaml 
   ( เป็นการ เอาไฟล์(ที่เราเขียน config ไว้แล้ว) มาทำการ Deploy  โดย ถ้าไม่เคย deploy ด้วยไฟล์นี้มาก่อน จะไปทำการ Deploy แต่ถ้าเคยแล้ว จะเป็นการ update ค่า )
   
- จากโค้ดที่ใช้ Deploy  Pod อธิบายบางส่วน
  apiVersion: v1        ใช้กำหนด version ของ API ของ kube-apiserver ที่เราจะคุยด้วย
  kind: Pod             ใช้กำหนดชนิดของ object ว่าเป็นอะไร(เช่น Pod,Service)
  metadata:             เป็นที่แสดง metadata ของ Pod 
    name: hello-kube    บอกว่า Podนี้ ชื่ออะไร 
    labels:             บอกว่า pod นี้ มีป้ายที่ชื่ออะไร
      app: hello-kub
  spec:                 ระบุ specification ของ Pod  ซึ่งประกอบด้วย 
    containers:         
      - image: johnr46/hello-kube:1.0   ระบุ image ที่จะถูก เอามารัน
        name: hello-kube                ระบุชื่อ ของ image        
        ports:
          - containerPort: 80           การเปิดใช้ Port หมายเลขเท่าไหร่
  ลองใช้คำสั่ง
 > kubectl.exe apply -f hello-kube-service.yaml

  เสร็จแล้ว ทดสอบเข้าไปดูที่ http://localhost:80 จะเห็นว่า app เราทำงานอยู่
  
- จากโค้ดที่ใช้ Deploy  Service อธิบายบางส่วน
  apiVersion: v1
  kind: Service
  metadata:
    name: hello-kube-service
    labels:
      app: hello-kube-service
  spec:                             จะสังเกตุเห็นว่า ส่วน Spec ต่างกัน 
    type: LoadBalancer              บอกชนิด Service
    selector:                       บอก Service นี้ให้วิ่งไปหา Pod ไหน ก็คือ วิ่งไปหา  pod ที่ติดป้ายว่า  app: hello-kube 
      app: hello-kube
    ports:
      - protocol: TCP
        port: 80
        targetPort: 80


เสร็จแล้วลบออกด้วย (ถ้าไม่ลบมันจะรกเฉยๆ ดูลำบากตอนจะทำแลปต่อไป)
 > kubectl delete pod hello-kube 
   ลบ pod ที่ชื่อ hello-kube

 > kubectl delete Service hello-kube
   ลบ Service ที่ชื่อ hello-kube



# สรุป Pod And Service
> Pod คือ หน่วยที่เล็กที่สุดที่ Kubernetes สามารถควบคุมได้ ใน pod อาจมีเพียง 1 container หรือมากว่านั้นก็ได้ แต่โดยปกติแล้วจะมี 1 container ที่ทำ logic ของ app นอกนั้นจะทำหน้าที่เป็นตัวคอยช่วยเหลือ เรียกว่า sidecar
> Service เป็น Load Balancer โดนทำหน้าที่หลักสองอย่างคือ 1) เป็นตัวแทนงานบริการ (Service) ซึ่งรวมถึงการประกาศชื่อของงานและการรับงานเข้ามา และ 2) กระจายงานที่รับมาส่งต่อให้แต่ละ Pod จากตัวอย่าง เป็นการสร้างทางรับ request แล้วส่งมาให้ Pod


## การติดตั้ง App เราจะไม่ติดตั้ง Container หรือ Pod ตรงๆ (หรือที่เรียกกันว่า Naked Pod) แบบข้างบนที่ผมพาทำ แต่จะใช้ Deployment ซึ่งการใช้ Deployment มีข้อดีหลายอย่าง เช่น
- Self-healing กรณีที่ Pod ตาย (crash) Deployment จะรัน Pod ใหม่มาแทนที่
- สามารถเพิ่มหรือลดจำนวน Pod ได้
- สามารถลงเวอร์ชั่นใหม่ (roll out) หรือถอยหลัง (roll back)

# การติดตั้ง App ผ่าน Deployment จะใช้สองอย่างคือ 
1. kubectl สำหรับติดต่อกับ Cluster เพื่อติดตั้ง 
2. ไฟล์ YAML (อ่านว่า ยา-แม่ว) ที่อธิบายลักษณะของ Deployment+Pod+Container

# การใช้งาน Kubernetes เบื้องต้น
การใช้งาน Kubernetes
1. ติดตั้ง App ของเราจาก Container
2. ตรวจสอบ Pod ที่รัน App
3. เปิด App เราสู่โลกภายนอก Cluster
4. ขยาย App
5. ติดตั้ง App เวอร์ชั่นใหม่

# ติดตั้ง App ของเราจาก Container
โค้ดที่ใช้ Deploy อธิบายบางส่น

apiVersion: apps/v1 # for versions before 1.9.0 use apps/v1beta2
kind: Deployment
metadata:
  name: hello-kube-deployment
- สี่บรรทัดแรกบอกว่า Kubernetes API ที่ใช้เป็นเวอร์ชั่นไหน และเรากำลังจะประกาศอะไร 
(ในที่นี้คือ Deployment) และชื่อของสิ่งที่เราประกาศ (ในที่นี้คือ hello-kube-deployment)
-------------------------------------------------------------------------
spec:
  selector:
    matchLabels:
      app: hello-kube
  replicas: 3
- อีกห้าบรรทัดต่อมา เป็นการประกาศ spec เบื้องต้นของ Deployment สองอย่างคือ label ของ Pod — matchLabels เพื่อให้ Deployment รู้ว่ากำลังดูแล Pods ไหนบ้างผ่าน label และจำนวนของ Pod — replicas (ในที่นี้คือ 3)
-------------------------------------------------------------------------
template:
    metadata:
      labels:
        app: hello-kube
    spec:
      containers:
      - name: hello-kube
        image: johnr46/hello-kube:1.0
        ports:
        - containerPort: 80 
- ที่เหลือเป็นการประกาศ template ของ Pod ว่ามี labels เป็นอะไร เพื่อใช้ match กับ Deployment (ตามที่บอกไปก่อนหน้า) และ spec ของ Pod ว่ามีกี่ Containers ชื่ออะไรบ้าง ใช้ Docker Image อะไร และมีการเปิด Port หมายเลขเท่าไหร่

# ทำการ Deploy 
  ใช้คำสั่ง
  > kubectl.exe apply -f deployment-hello-kube.yaml
    
    เป็นการทำการ Deploy ด้วยไฟล์ deployment-hello-kube.yaml
  

# เช็คสถานะของการติดตั้ง
  ใช้คำสั่ง
  > kubectl get deployment


# ตรวจสอบ Pod ที่รัน App
คำสั่งที่ใช้ตรวจสอบ Pod มีไม่เยอะครับ โดยทั่วไปก็ใช้ตามนี้
 > kubectl get pod เพื่อดูลิสต์ของ Pod
 > kubectl describe pod/POD_NAME เพื่อดูข้อมูลลักษณะและสถานะของ Pod

# เปิด App เราสู่โลกภายนอก Cluster
- โดยปกติแล้ว เราจะเปิด App Pod ของเราสู่โลกภายนอกด้วยสองวิธี แต่ผมขอยกมาแค่  คือใช้ Service
- Service จะรู้ได้อย่างไรว่าจะส่งต่อ request ไปให้ Pods ไหน? Service จะเหมือนกับ Deployment และส่วนอื่นๆของ Kubernetes ที่จะใช้ Label ในการเชื่อมโยง

โดยผมจะทำการ สร้าง Service ให้เข้าใช้งาน pod ที่ Deploy ไว้ และขออธิบายโค้ดบางส่วนก่อน

apiVersion: v1
kind: Service
metadata:
  name: hello-kube-service
  labels:
    app: hello-kube-service
spec:
  type: LoadBalancer 
  selector:
    app: hello-kube
  ports:
    - protocol: TCP
      port: 80
      targetPort: 80

สังเกตุ มีส่วน apiVersion, kind, และ metadata คล้ายๆกัน ต่างกันที่ค่านิดหน่อย
ส่วน spec จะมีลักษณะต่างกัน แน่นอนเพราะว่า Service กับ Deployment มันคนละอย่างกัน
spec ของ Service ผมจะบอกว่า 
- เป็น LoadBalancer ที่สร้าง endpoint ขึ้นมาให้เข้าถึงได้
- ส่งต่อ request ไปที่ Pod(s) ที่มี Label app: hello-kube
- มีการส่งต่อ request จาก Port 80 ของ Node ไปให้ที่ Port 80 ของ Pod 

# ทีนี้เรามาทำการ สร้าง Service 

> kubectl.exe apply -f expose-hello-kube.yaml 

   เป็นการทำการ สร้างService ด้วยไฟล์ expose-hello-kube.yaml 

  เสร็จแล้ว ทดสอบเข้าไปดูที่ http://localhost:80 จะเห็นว่า app เราทำงานอยู่

> kubectl get service 
  เพื่อดู Service ว่าสร้างมาหรือยัง

# ขยาย App หรือที่เรียกว่า Scale 
- จากบทที่ผ่านมา เกี่ยวกับการ Deployment เรารู้ว่า จำนวนพอด ขึ้นอยู่กับ replicas
ดังนั้นเราจะ scale ด้วยการแก้ไขจำนวน replicas ซึ่งเราจะทำดังนี้

1. ใช้ command 

  # kubectl scale --replicas=count_for_pod deployment/name-deployment

  จากบทที่ผ่านมา เรารู้ว่า deployment ของเราชื่อ hello-kube-deployment และ เราอยาก Scale ให้เป็น 10 
  ดังนั้น จาก command ข้างบนเราจะแปลงเป็นคำสั่งเราได้ดังนี้

  > kubectl scale --replicas=10 deployment/hello-kube-deployment
    
   เป็นการ เพิ่มจำนวน pod ให้เป็น 10 
   เสร็จแล้วใช้คำสั่ง
  
  > kubectl.exe get deployment/hello-kube-deployment  

    เพื่อ check สถานะว่า scale ได้หรือยัง


2. เราไปแก้ไขไฟล์ที่ใช้ Deploy (จากตัวอย่างเป็น deployment-hello-kube.yaml  หาดูตรงคำว่า replicas แล้วแก้เป็นจำนวนที่ต้องการ เช่น ผมแก้เป็น 10 ) เสร็จแล้วก็เอาไฟล์นั้นไป Deploy  

> kubectl.exe apply -f deployment-hello-kube.yaml 


# ติดตั้ง App เวอร์ชั่นใหม่
- คำสั่งที่เราใช้
   - การติดตั้งเวอร์ชั่นใหม่จะเป็น  kubectl set image 
   - ส่วนการถอยหลัง (ยกเลิกการติดตั้ง) และการเช็คประวัติเป็นคำสั่งเดียวกัน kubectl rollout 

- ลองเช็คประวัติกันหน่อย 
  ลองใช้คำสั่ง
  > kubectl rollout history deployment/hello-kube-deployment
    เอาไว้เช็คประวัติ
  จะเห็นว่า เป็น 1 เพราะผมยังไม่ได้เปลี่ยน version imange 

- ลองดู Version image กันหน่อย
  ลองใช้คำสั่ง
  > kubectl describe deployment/hello-kube-deployment | grep Image 
    เอาไว้ดู Version image
  จะเห็นว่า  ( ตอนนี้ผมใช้ image: johnr46/hello-kube:1.0) ผมจะลองเปลี่ยนเป็น 2.0

- ลองเปลี่ยน Version image กันหน่อย
  ลองใช้คำสั่ง 
  > kubectl set image deployment/hello-kube-deployment hello-kube=johnr46/hello-kube:2.0  --record
   
   คำสั่งนี้ เป็นการติดตั้ง hello-kube  เวอร์ชั่น 2.0 ซึ่งในการติดตั้งเราจะต้องบอกด้วยว่าตั้งที่ Container ไหน 
   hello-kube=johnr46/hello-kube:2.0  เนื่องด้วยใน Pod ของเราสามารถมีได้หลาย Containers

- ลองดูว่าเปลี่ยนสำเร็จหรือยัง เผื่อว่าจะใช้เวลานาน
  ลองใช้คำสั่ง
  > kubectl rollout status deployment/hello-kube-deployment

   คำสั่งนี้ เป็นการเช็ค status ของการ roll out ถ้าเรามีหลาย Pod และ Image ใช้เวลาสตาร์ตนาน (เช่น Java + Bootstrap) เราจะเห็นว่า Kubernetes จะค่อยๆ ติดตั้งไปทีละ Pod(s) จนครบ

- ลองเช็คประวัติกันหน่อย
  ลองใช้คำสั่ง
  > kubectl rollout history deployment/hello-kube-deployment

    เอาไว้เช็คประวัติ
    จากนั้น ลองเช็คประวัติดู ซึ่งจะเห็นว่า version เป็น 2.0 แล้ว หรือจะดูที่  http://localhost:80 (ซึ่งหน้าเว็บผมเขียนบอก version image อยู่ จะเห็นเป็น 2.0)
  
# ถ้าสมมุติเราเช็คแล้วว่า hello-kube เวอร์ชั่น 2.0 ทำงานไม่ปกติ เราจะถอยหลังกลับไปเวอร์ชั่นก่อนหน้าได้ด้วย kubectl rollout undo
- ถอยหลัง Version image
  ลองใช้คำสั่ง
  > kubectl rollout undo deployment/hello-kube-deployment
  
    ถ้าเปลี่ยนสำเร็จ จะขึ้นว่า  rolled back

- ลองดู Version image กันหน่อย ว่าเปลี่ยนยัง
  ลองใช้คำสั่ง
  > kubectl describe deployment/hello-kube-deployment | grep Image 
    เอาไว้ดู Version image

    จะเห็นว่า  ( ก่อนหน้า ผมใช้ image: johnr46/hello-kube:2.0) จะเปลี่ยนกลับมาเป็น 1.0 แล้ว 
    หรือจะดูที่  http://localhost:80 ว่า Version กลับเป็น 1.0 หรือยัง

# ก็ขอจบ lab เพียงเท่านี้ครับ


ref เพิ่มเติมเกี่ยวกับการ Deployment 
 > https://kubernetes.io/docs/concepts/workloads/controllers/deployment/




