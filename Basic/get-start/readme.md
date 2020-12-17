# basic get-start
# kubectl command
syntax ในการใช้ command kubectl เป็นดังนี้
kubectl [command] [type] [name] [flag]
1. command: verbs ที่เราจะทำกับ resource 
  - (list of commands) -> https://kubernetes.io/docs/reference/kubectl/overview/#operations
2. type: type ของ resources ที่เราจะจัดการกับมัน 
  - (list of resource types) -> https://kubernetes.io/docs/reference/kubectl/overview/#resource-types
3. name: ชื่อของ resources นั้นๆ
4. flag: option เสริมระบุรายละเอียดของของคำสั่ง
> https://igokuz.com/%E0%B8%84%E0%B8%B3%E0%B8%AA%E0%B8%B1%E0%B9%88%E0%B8%87-kubernetes-101-e77a7f6ca2bc

# Basic Concept 
- Manifest file ก่อนจะกล่าวถึง ขออธิบายสั้นๆ ก่อน
- การ Deploy มี 2 แบบ 

1. Command line  

 > ex. kubectl run hello-kube --image=fhsinchy/hello-kube --port=80 
   แล้วลองใช้คำสั่ง  kubectl get pod ลองไปปิดดูทีนี้
   ทีนี้อยากใช้งาน แต่แน่นอนว่าใช้ไม่ได้ ทำไง? Service จะมาแก้ปัญหานี้ ลองใช้คำสั่ง
 > ex. kubectl expose pod hello-kube --type=LoadBalancer --port=80

2. manifest file (เป็น แพททริกที่ดี ที่ควรจะทำ ) 
   - บอกโครงสร้าง ของ Resource ทั้งหมดที่เราจะสร้าง , อธิบาย  การทำลักษณะนี้เรียกว่า  Infrastructure as Code (Iac)  ควรจะทำแบบนี้  (สามารถ Control Infrastructure  ได้ด้วยไฟล์นี้)

# https://www.freecodecamp.org/news/the-kubernetes-handbook/#kubernetes-architecture
# https://kubernetes.io/docs/concepts/


# Demo And Lab 
การใช้งาน Kubernetes
1. ติดตั้ง kube
2. ติดตั้ง App ของเราจาก Container
3. ตรวจสอบ Pod ที่รัน App
4. เปิด App เราสู่โลกภายนอก Cluster
5. ขยาย App
6. ติดตั้ง App เวอร์ชั่นใหม่

## Manifest อธิบายนิดๆ หน่อยๆ 

# Pod คือ หน่วยที่เล็กที่สุดที่ Kubernetes สามารถควบคุมได้ ใน pod อาจมีเพียง 1 container หรือมากว่านั้นก็ได้ แต่โดยปกติแล้วจะมี 1 container ที่ทำ logic ของ app นอกนั้นจะทำหน้าที่เป็นตัวคอยช่วยเหลือ เรียกว่า sidecar
> create Simple Pod
   # kubectl.exe apply -f get-start/pod/hello-kube-pod.yaml 

> ตรวจสอบ status ของ pod ด้วย kubectl get pods ดังตัวอย่าง
   # kubectl get pods
  
> ดูแบบละเอียดๆ 
   # kubectl get pod firstpod -o yaml

> จากโค้ดที่ใช้ Deploy  Pod อธิบายบางส่วน
   apiVersion: ใช้กำหนด version ของ API ของ kube-apiserver ที่เราจะคุยด้วย
    > All of API : https://dev.to/peepeepopapapeepeepo/lfs258-4-15-kubernetes-apis-and-access-3780
   - kind: ใช้กำหนดชนิดของ object นี้ นั้นก็คือ Config เสมอ
   - metadata: เป็นที่แสดง metadata ของ Pod 
   - name  :บอกว่าชื่ออะไร 
   - spec: ระบุ specification ของ pod
   - image:   ระบุชื่อ image ที่จะถูก run ใน container นั้น
   - name: prefix ของชื่อ pods


# ติดตั้ง app ของเราจาก Container Image 
 - การติดตั้ง App เราจะไม่ติดตั้ง Container หรือ Pod ตรงๆ (หรือที่เรียกกันว่า Naked Pod) แต่จะใช้ Deployment
 - การติดตั้ง App ผ่าน Deployment จะใช้สองอย่างคือ kubectl สำหรับติดต่อกับ Cluster เพื่อติดตั้ง และ ไฟล์ YAML (อ่านว่า ยา-แม่ว)ที่อธิบายลักษณะของ Deployment+Pod+Container

# การ Deployment
> ใช้คำสั่ง 
   # kubectl apply -f deployment/deployment-nginx.yaml

   apiVersion: apps/v1
   kind: Deployment
   metadata:
      name: nginx-deployment

  > อธิบาย 
   API ที่ใช้เป็นเวอร์ชั่นไหน และเรากำลังจะประกาศอะไร (ในที่นี้คือ Deployment) และชื่อของสิ่งที่เราประกาศ        
----------------------------------------------------------------------------------------
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 1

  > อธิบาย 
    - spec: ใช้ระบุ specification ในการสร้าง object
    - selector: ระบุ label ของ pod ที่ replicaSet จะต้องควบคุม
    - matchLabels: เป็น condition ในการเลือก label
    - replicas: จำนวน pods ที่ replicaSet จะต้องสร้าง
----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80
   > อธิบาย
    - template เป็น template   สำหรับ replicaSet ที่ใช้ในการสร้าง pods ในความดูแลของมัน
    - spec เป็นการระบุ specification ของ pod
    - containers เป็นชื่อ list ที่บอกว่าหลังจากนี้จะเป็น spec ของแต่ละ container ใน pods

ที่เหลือเป็นการประกาศ template ของ Pod ว่ามี labels เป็นอะไร เพื่อใช้ match กับ Deployment (ตามที่บอกไปก่อนหน้า) และ spec ของ Pod ว่ามีกี่ Containers ชื่ออะไรบ้าง ใช้ Docker Image อะไร และมีการเปิด Port หมายเลขเท่าไหร่
---------------------------------------------------------------------------------------------------------------------------------------------
kubectl apply -f deployment-nginx.yaml

# เริ่ม Deployment
 - หลังจากเราสร้างไฟล์ YAML แล้ว (สมมุติว่าชื่อ deployment-nginx.yaml) เราสามารถรัน kubectl เพื่อติดตั้ง Deployment ได้ด้วยคำสั่ง
   > kubectl apply -f deployment-nginx.yaml
 - ดูสถานะการติดตั้งด้วย
   > kubectl get deployment
 - หรือจะดู pods ที่รันมา
   > kubectl get pod

# Debug
 - ถ้าจะทำการ debug อันที่ Deploy มา  ก็ลอง port-forward ดู ( ตามตัวอย่างผม deploy nginx เปิดรอไว้ที่ 80 เราจะ forward จาก localhost:5580 ไปยัง port 80 ที่ได้ deploy ไว้)
    ex.   
    # Listen on port 8888 locally, forwarding to 5000 in the pod >  kubectl port-forward pod/mypod 8888:5000
   > kubectl port-forward pod/nginx-deployment-66b6c48dd5-97rld 5580:80 
     ถ้าสำเร็จจะขึ้นเป็น 
     Forwarding from 127.0.0.1:5580 -> 80
     Forwarding from [::1]:5580 -> 80
     ตอนจะเลิกใช้ก็ ctrl+c

# check สถานะ & ดู log
 - ตรวจสอบ pod ที่รัน app ( Pod เป็นหน่วยการทำงานที่เล็กที่สุดของ Kubernetesทุก Pod จะมี Private IP Address ซึ่งจะรู้จักกันเฉพาะใน Cluster เท่านั้น)
 - คำสั่งที่ใช้ตรวจสอบ Pod มีไม่เยอะครับ โดยทั่วไปก็ใช้ตามนี้
   - ดู pod ทั้งหมด
    > kubectl get pod
   - เพื่อดูข้อมูลลักษณะและสถานะของ pod
    > kubectl describe pod/POD_NAME หรือ
    > kubectl get pod POD_NAME -o yaml
    > kubectl get pod POD_NAME -o json
   - ดู log ของ Pod ซึ่งออกจาก stdout และ stderr และถ้าเราอยากตามดูไปเรื่อยๆ ให้ใช้ -f
    > kubectl logs POD_NAME 
    > kubectl logs POD_NAME -f
   - การเข้าไปใน pod (เหมือนกับ docker exec -it container_id bash) โดยparameter สุดท้าย (bash อาจจะต้องเปลี่ยนไปตาม docker image)
    > kubectl exec -it POD_NAME bash 

# Scale แบบ manual เบื้องต้น 
   - แบบแก้ไฟล์ yaml 
    > kubectl edit deployment/nginx-deployment
    Ex. 1. kubectl get deployment ดูชื่อ Deployment ที่จะแก้ โดยจะแก้จาก 3 เป็น 10
        2. kubectl edit deployment/nginx-deployment จะมี vim หรือ notepad ก็แล้วแต่ จะเปิดไฟล์มาให้แก้ ให้หาคำว่า replicas ที่อยู่ใต้ spec จากนั้นเซฟไฟล์
        3. เสร็จแล้วใช้คำสั่ง kubectl get deployment เพื่อดูสถานะ

   - อีกแบบ สั้นๆ ง่ายดี ใช้ kubectl ช่วย เช่นถ้าจะเพิ่มจาก 1 เป็น 3 
    > kubectl scale --replicas=3 deployment/deplyment_name
    Ex. 1. ใช้ kubectl get deployment ดูว่า  deployment ไหน ที่จะทำการ  scale เช่น ผมได้ชื่อมาว่า nginx-deployment
        2. kubectl scale --replicas=3 deployment/nginx-deployment
        3. ใช้ kubectl get deployment ดูว่าเป็นไงบ้าง

   - ดูเพิ่มเติมได้ โดยใช้คำสั่ง kubectl scale -h


# Delete Deployment 
 - To delete the Deployment, the ReplicaSet, and the Pods that are running the nginx-deployment application, enter this command: ( ลบทิ้งทั้ง Deployment ที่ใช้ชื่อว่า nginx-deployment )
    > kubectl delete deployment nginx-deployment  ( ปล. ถ้าแอปนั้นกำลังทำงาน ก็ไม่สนใจ จะถูกปิดและลบทิ้งทันที)


# เปิด App เราสู่โลกภายนอก
  # Service เป็น Load Balancer โดนทำหน้าที่หลักสองอย่างคือ 1) เป็นตัวแทนงานบริการ (Service) ซึ่งรวมถึงการประกาศชื่อของงานและการรับงานเข้ามา และ 2) กระจายงานที่รับมาส่งต่อให้แต่ละ Pod
 - เปิด App เราสู่โลกภายนอก Cluster วิธีแรกคือใช้ Service กับ วิธีที่สองคือใช้ Ingress
   1. Service มี 4 แบบ
     1.1 ClusterIP  เอาไว้ใช้คุยกันระหว่าง Pods ภายใน Cluster ต่อจากข้างนอกเข้ามาไม่ได้
     1.2 NodePort เอาไว้ต่อจากข้างนอกเข้ามาหา Pods ข้างใน โดยเปิด Port รอที่ Node หลังจากนั้นจากส่งต่อ request ที่เข้ามาที่ Port ไปให้ Pod
     1.3 LoadBalancer เอาไว้ต่อจากข้างนอกเข้ามาหา Pods ข้างใน ใช้ได้เฉพาะกรณีที่รัน Kubernetes บน Cloud เช่นถ้าเป็น Kops หรือ EKS บน AWS เราก็จะได้ Elastic Load Balancer (ELB) สำหรับแต่ละ Service
     1.4 ExternalName เอาไว้ต่อจากข้างในออกไปข้างนอก ไม่มีการส่งต่อ traffic จะส่งต่อแค่ชื่อเครื่อง (DNS CNAME) ที่ใช้ๆกันก็เช่นเวลาที่เรารัน managed database บน cloud ชื่อมันจะยาวๆและอาจจะมีเปลี่ยนได้ กรณีนี้เราสามารถสร้าง ExternalName Service มาตั้งชื่อใหม่เข้าใจง่ายไม่เปลี่ยนแปลงตามชื่อ database ไว้ให้ Pods ใน Cluster เราใช้ได้ง่ายๆ


# สร้าง Service
 - สร้างแบบ Declarative
  > service-nginx.yaml
--------------------------------------------------------------------------------------------------------------------------------
apiVersion: v1
kind: Service
metadata:
  name: nginx-service
  labels:
    app: nginx-service
มีส่วน apiVersion, kind, และ metadata คล้ายๆกัน ต่างกันที่ค่านิดหน่อย
--------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------
spec:
  type: NodePort        -- เป็น NodePort — ถ้าใครรัน Docker Desktop ก็จะต่อเข้า localhost ได้เลย
  selector:             -- ส่งต่อ request ไปที่ Pod(s) ที่มี Label app: nginx
    app: nginx 
  ports:
    - name: http
      port: 80
      nodePort: 30080   -- มีการส่งต่อ request จาก Port 30080 ของ Node ไปให้ที่ Port 80 ของ Pod

ส่วน spec จะมีลักษณะต่างกัน แน่นอนเพราะว่า Service กับ Deployment มันคนละอย่างกัน
--------------------------------------------------------------------------------------------------------------------------------

- สร้าง Service โดยไม่ต้องใช้ไฟล์ YAML (สร้างแบบ Imperative) ก็สามารถทำได้
  # กรณีที่สร้างแบบนี้ เราจะไม่สามารถกำหนด Port ที่เราจะเปิดรอที่ Node ได้ ระบบจะหาให้เอง
 >  kubectl expose deployment/name_deployment --name=name_service --port=port_number_for_deployment --type=NodePort
  Ex. 1. ใช้คำสั่ง kubectl get deployment เพื่อดู deployment name ที่เราจะ สร้าง service และเข้าถึง เช่นได่ชื่อมาว่า nginx-deployment
      2. kubectl expose deployment/nginx-deployment --name=nginx-service --port=80 --type=NodePort
      3. ใช้คำสั่ง kubectl get service   หรือ 
          kubectl get -o jsonpath=”{.spec.ports[0].nodePort}” service nginx-service ( nginx-service เป็นชื่อที่ผมตั้งเอาจากข้อ 2 )
         เพื่อดูว่า ได้ port อะไรออกมาให้ใช้ เช่นผมได้ 32085 ก็เอาไปเปิดด้วย http://localhost:32085/  เพื่อดูว่าใช้ได้



# เปลี่ยน version Image 
 - เช็คประวัติ
  > kubectl rollout history deployment/image_name
  Ex. 1. kubectl rollout history deployment/nginx-deployment  โดยปกติ ถ้ายังไม่เคยเปลี่ยนเวอร์ชั่น จะ ยังไม่มีประวัติอะไรเลย (แค่ 1 )
- ดู image version
  > kubectl describe deployment/image_name | grep Image
  Ex. 1. kubectl describe deployment/nginx-deployment | grep Image  จะเห็นว่าเป็น nginx:1.14.2 ลองไปดูต่อที่ https://hub.docker.com/_/nginx เก่าวะะ ลองเปลี่ยนเป็น 1.19.5
- คำสั่งในการเปลี่ยน update version image 
 > kubectl set image deployment/image_name container_name=name_image:number_version_for_change --record   ( --record บันทึกประวัติการเปลี่ยน)

  Ex. 1. kubectl set image deployment/nginx-deployment nginx=nginx:1.19.5 --record
         คำสั่งแรกเป็นการติดตั้ง nginx เวอร์ชั่น 1.19.5 ซึ่งในการติดตั้งเราจะต้องบอกด้วยว่าตั้งที่ Container ไหน nginx=nginx:1.19.5— เนื่องด้วยใน Pod ของเราสามารถมีได้หลาย Containers

      2.  kubectl rollout status deployment/nginx-deployment การเช็ค status ของการ roll out ถ้าเรามีหลาย Pod และ Image ใช้เวลาสตาร์ตนาน (เช่น Java + Bootstrap) เราจะเห็นว่า Kubernetes จะค่อยๆ ติดตั้งไปทีละ Pod(s) จนครบ

      3. kubectl rollout history deployment/nginx-deployment เอาไว้เช็คประวัติ

# ถ้าสมมุติเราเช็คแล้วว่า nginx เวอร์ชั่น 1.19.5 ทำงานไม่ปกติ เราจะถอยหลังกลับไปเวอร์ชั่นก่อนหน้าได้ด้วย kubectl rollout undo
 >  kubectl rollout undo deployment/deployment_name
 Ex. 1. kubectl rollout undo deployment/nginx-deployment

ref เพิ่มเติมเกี่ยวกับการ Deployment 
 > https://kubernetes.io/docs/concepts/workloads/controllers/deployment/
