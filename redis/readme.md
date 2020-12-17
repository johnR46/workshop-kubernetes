Data types ที่รองรับ
Redis จะ map key กับ data type ซึ่งเป็นจุดที่แตกต่างจากฐานข้อมูล key-value ตัวอื่นที่ ไม่รองรับ key แบบ string อย่างเดียว แต่จะรองรับ key ต่างๆ ได้ดังนี้
String — เป็นชนิดข้อมูลพื้นฐาน
List —เป็น list ของ String จัดเรียงโดย insertion order
Set — เป็น set ของ String ที่ไม่จัดเรียง
Hash table — เป็นตารางที่เก็บ key และ value
Sorted set — เป็น set ของ String ที่จัดเรียงและไม่ซำ้กัน
Bitmap — ใช้สำหรับคำสั่งพิเศษที่จัดการค่าของ String เหมือน array ของ bits
HyperLogLogs — เป็นโครงสร้างข้อมูลความน่าจะเป็นใช้สำหรับการประมาณ cardinality ของ set


set hello world  เก็บข้อมูลด้วย key = hello data = world
get hello ดึงข้อมูลออกมาด้วย key hello
set counter 1
บวกเลข ทีละหนึ่งด้วยคำสั่ง incr
incr counter 
บวกเลขด้วยคำสั่ง INCRBY key value 
INCRBY counter -5


> implement by spring
 > ทำแลป run Redis ด้วย docker แล้ว เอา Spring จาก localhost bind port เข้าไป
