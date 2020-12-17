เป็นไงบ้าง มึนๆ มั้ย ไฟล์ YAML แต่อย่าเพิ่งปวดหัวไป เรามีตัวช่วย(เย่)

ข่าว
>   https://www.blognone.com/node/117420
>   https://www.somkiat.cc/cdk8s-cloud-development-kit-for-kubernetes/
Reposistory ทางการ
>   https://github.com/awslabs/cdk8s/
เว็บหลัก
>   https://cdk8s.io/docs/v1.0.0-beta.5/getting-started/


ช้าอยู่ใย เริ่ม (ผมเลือก typeScript )

# Install the CLI
1. npm install -g cdk8s-cli หรือ  yarn global add cdk8s-cli (ตัวใดตัวนึง)
2. พอทำแล้ว มันควรจะใช้ได้ แต่ถ้าลองใช้ cdk8s แล้วนิ่ง ให้ไป set path เพิ่ม  
    โดยปกติจะอยู่ตรงนี้ > C:\Users\user_name\AppData\Local\Yarn\bin

# New Project 
1. mkdir hello-cdk8s
2. cd hello-cdk8s
3. cdk8s init typescript-app

้้เข้าไปดูโครงสร้างโปรเจ็ค
 # จะการเขียน code ในไฟล์ main.ts
 # out อยู่ที่ dist

เริ่มจริงๆ ซักที
> เช่น  Deploy nginx ซึ่งต้องการ resources 2 ตัวคือ
 - Deployment
 - Service
 อะไรที่โยงมันเข้าด้วยกัน ? ฮั่นแน่ ลืมยัง

 