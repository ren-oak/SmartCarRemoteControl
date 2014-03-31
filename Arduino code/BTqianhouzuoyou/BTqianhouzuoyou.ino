int EN2=16;
int EN3=17;
int EN4=18;
int EN5=19;
void ting(void)
 {
   digitalWrite(EN2,LOW);
   digitalWrite(EN3,LOW);
   digitalWrite(EN4,LOW);
   digitalWrite(EN5,LOW);
 }
 void you(void)
 {
   digitalWrite(EN2,LOW);
   digitalWrite(EN3,HIGH);
   digitalWrite(EN4,LOW);
   digitalWrite(EN5,HIGH);
 }
 void zuo(void)
 {
   digitalWrite(EN2,HIGH);
   digitalWrite(EN3,LOW);
   digitalWrite(EN4,HIGH);
   digitalWrite(EN5,LOW);
 }
 void qian(void)
 {
   digitalWrite(EN2,HIGH);
   digitalWrite(EN3,LOW);
   digitalWrite(EN4,LOW);
   digitalWrite(EN5,HIGH);
 }
 void hou(void)
 {
   digitalWrite(EN2,LOW);
   digitalWrite(EN3,HIGH);
   digitalWrite(EN4,HIGH);
   digitalWrite(EN5,LOW);
 }
 void setup()
 {
   int i;
   for(i=16;i<=19;i++)
   pinMode(i,OUTPUT);
   Serial.begin(9600);
   
 }
 void loop()
 {
   char val;
   while(1)
   {
     val=Serial.read();
     if(val!=-1)
      {
        switch(val)
        {
          case'a':
          qian();
          delay(50);
          break;
           case'b':
          hou();
          delay(50);
          break;
           case'l':
          zuo();
          delay(50);
          break;
          case'r':
          you();
          delay(50);
          break;
           case's':
          ting();
         // delay(50);
          break;
        
          
        }
      }
   }
 }
 
 
 
 
 
