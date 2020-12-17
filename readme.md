# Get Start
0. Overview K8s > https://drive.google.com/file/d/107NNfDNpuIVshCVe0DEzvG5YhIQM5ODi/view?usp=sharing

Get Start 
- VM , Cotainer , Docker 
1. VM คอนเช็ปคือการจำลองเครื่อง   Ex. Vmware เป็นซอฟแวร์ที่ Implement แนวคิดการทำ VM 
2. Cotainer  คอนเช็ปคือการสร้าง Group ขัง Resource   Ex. Docker เป็นซอฟแวร์ที่ Implement ตามแนวคิด Containers (Docker ดังสุดในตลาด)
อนาคต -> แล้วถ้า Containers ดีขนาดนี้ แล้ว VM จะหายไปเลย ? ผมต้องเลิกใช้ ?
ตอบ ไม่  , Vm ดั้งเดิมถูกทำลายหรือเปลี่ยนไปใช้ Containers หมดอะไม่ เพราะ VM จะถูกใช้ต่อไป สองอย่างคือ 
1. Legency Software ยังไงก็ต้องใช้ Software 
2. Database on  Production  (แนะนำ อย่าไปรัน Database บน Containers ไม่ใช่รันไม่ได้ แต่ดูเเลยากเฉยๆ)

Concept สำคัญที่ทำให้ Docker ครองโลก (จะเห็นว่าที่ผ่านๆ มา ไม่เห็นอันไหนดัง)
ตอบ เพราะ Docker build เสร็จ(Create Docker Image ) เสร็จแล้ว ไป SHIP(ตัวกลางในการแลกเปลี่ยน) (เก็บ Docker image, เช่น Dokcer hub)  เสร็จแล้วเอาไปรันที่ไหนก็ได้ 
(ที่ไหนก็ได้ที่ Docker รันได้)
 (Build , Ship , Run concept 3 ตัวนี้ทำให้ Docker ครองโลก)

Docker on Server 
Docker เกิดมาเพื่อรันแบบ Stand Alone
(ความหมายคือรันบนเครื่องเดียว อยากได้เครื่องไหนไปรันเองซะ)
ต่อไป ถ้ามีหลายๆ Containers ละ ใครจะเป็นคนดูให้ 
เช่น Load Balance , Node ไหนเหลือ Resource พอที่จะรัน Containers เพิ่ม , Node ไหนตาย Node ไหนยังอยู่  ตอนสร้างNode ใหม่  Load Balanced จะชี้ไปหาเครื่องใหม่ยังไง? 



# สิ่งนี้ทำให้เกิด  K8s
- K8  คือ Software ในการทำ Containers Cluster (คอยดูแล)
K8s เกิดมาจากประสบการณ์ของ Google 15 ปี ในการดูแลระบบภายใน ก็เลยทำ K8 ขึ้นมาแล้วตั้งชือว่า  Kubernetes แล้วทำเป็น Opensource เสร็จแล้ว ส่งเข้าโครงการ Opensource 

K8S Architecture 
1 K8 Master  ทำหน้าที่ คอยดูแล Node  
2 Node ( มีหน้าที่เป็นแค่ WorkLoad ) คือ Containers ทั้งหลายแหล่ที่รัน จะอยู่ใน Node หมด

- การสั่งงาน จะไม่สั่งไปที่ Node โดยตรง แต่จะสั่งไปที่ Master ผ่าน API , หรืออย่างอื่น

สรุป  Dev สนใจแค่ หน้าจอ หรือ API ที่จะส่งงานมา deploy ส่วนข้างหลัง  config อะไร Load ยังไง Scale อะไร เป็นหน้าที่Infra ทำให้  ดังนั้น  K8 ก็เลยเป็น PaaS Platform as A Service


K8s Platform แบ่งเป็นสามกลุ่มใหญ่ๆ 
 - Distribution  เหมือน Linux (เป็นแค่ Kernel) แล้วจะยัดอะไรเพิ่มเข้าไป เกิดเป็นของใหม่มาเช่น Ubuntu Debial ตรงนี้ เรียกว่า Distribution  
   ( จะเห็นว่า Openshift เป็นหนึ่งใน Distribution   ของ K8s)
   - https://thenewstack.io/find-perfect-kubernetes-distribution/
- Installer (ตัวช่วยในการติดตั้ง)
- Hosted  (เห้ย ขี้เกียจลงเอง ขอแบบได้ทั้งหมดเลยได้ป่าว ) ก็จะเป็นแบบพร้อมใช้มาเลย(Hosted) 

1. setup > https://docs.google.com/presentation/d/1TMFGw2jXgB3t_d8QUUJ1fM2x2PNQ140xsOnqLhGpyBo/edit?usp=sharing
2. Gogo Lab
3. basic get-start

