# spring boot mysql k8s node
ตัวอย่างการใช้งาน ( ผมหามาแล้วเอามาลองเขียนตาม)
> Video 
    https://www.youtube.com/watch?v=ThAkKPAwdpQ&fbclid=IwAR0T9hve6dxJW9KLiEjZZ1vG7uaISwiYUwuw_gPjqvpvz70XB7zfbejpr94

> Github 
 https://github.com/techtter/students-app


# How to run
1. build jar file 
mvn -Dmaven.test.skip=true clean install     ## to skip the unit tests (ถ้าเทสก็บึ้มเพราะ db ยังไม่มีไง)

2. build docker file
docker build -t employee-app:1.1 .

3. ไปที่ โฟล์เดอร์ k8s แล้ว
 - kubectl apply -f mysql-configmap.yaml 
 - kubectl apply -f mysql-admin-secrets.yaml
 - kubectl apply -f mysql-user-secrets.yaml 
 - kubectl apply -f mysql-deployment.yaml 
 - kubectl apply -f employee-deployment.yaml 

4. เข้าไปดูและใช้งานที่ 
 > http://localhost:30163/api/employees