double Fahrenheit(double celsius) 
{
        return 1.8 * celsius + 32;
}    //摄氏温度度转化为华氏温度

double Kelvin(double celsius)
{
        return celsius + 273.15;
}     //摄氏温度转化为开氏温度

// 露点（点在此温度时，空气饱和并产生露珠）
// 参考: http://wahiduddin.net/calc/density_algorithms.htm 
double dewPoint(double celsius, double humidity)
{
        double A0= 373.15/(273.15 + celsius);
        double SUM = -7.90298 * (A0-1);
        SUM += 5.02808 * log10(A0);
        SUM += -1.3816e-7 * (pow(10, (11.344*(1-1/A0)))-1) ;
        SUM += 8.1328e-3 * (pow(10,(-3.49149*(A0-1)))-1) ;
        SUM += log10(1013.246);
        double VP = pow(10, SUM-3) * humidity;
        double T = log(VP/0.61078);   // temp var
        return (241.88 * T) / (17.558-T);
}

// 快速计算露点，速度是5倍dewPoint()
// 参考: http://en.wikipedia.org/wiki/Dew_point
double dewPointFast(double celsius, double humidity)
{
        double a = 17.271;
        double b = 237.7;
        double temp = (a * celsius) / (b + celsius) + log(humidity/100);
        double Td = (b * temp) / (a - temp);
        return Td;
}

#include "dht11.h"

dht11 DHT11;

#define DHT11PIN 2
#define BUZZPIN 6
#include <Arduino_FreeRTOS.h>
#include <queue.h>
float temperature;
QueueHandle_t xQueue1;
QueueHandle_t xQueue2;

void taskDHT11(void *pvParameters);
void taskConform(void *pvParameters);
void taskBuzz(void *pvParameters);


void setup()
{
  temperature = 0.0;
  pinMode(BUZZPIN,OUTPUT);
  Serial.begin(9600);
  digitalWrite(BUZZPIN,HIGH);
  xQueue1 = xQueueCreate(5, sizeof(float));
  xQueue2 = xQueueCreate(5, sizeof(float));
  xTaskCreate(
    taskDHT11
    ,  "DHT11"   // A name just for humans
    ,  128  // Stack size
    ,  NULL
    ,  2  // priority
    ,  NULL );

  xTaskCreate(
    taskConform
    ,  "Conform"
    ,  128 // This stack size can be checked & adjusted by reading Highwater
    ,  NULL
    ,  1  // priority
    ,  NULL );
    
   xTaskCreate(
    taskBuzz
    ,  "Buzz"
    ,  128 // This stack size can be checked & adjusted by reading Highwater
    ,  NULL
    ,  1  // priority
    ,  NULL );
   
   vTaskStartScheduler();
  //Serial.println("DHT11 TEST PROGRAM ");
  //Serial.print("LIBRARY VERSION: ");
  //Serial.println(DHT11LIB_VERSION);
  //Serial.println();
}

void loop()
{
  delay(1000);
}
void taskDHT11(void *pvParameters){
  for(;;){
    int chk = DHT11.read(DHT11PIN);

    Serial.print("Read sensor: ");
    switch (chk)
    {
      case DHTLIB_OK: 
            Serial.println("OK"); 
            break;
      case DHTLIB_ERROR_CHECKSUM: 
            Serial.println("Checksum error"); 
            break;
      case DHTLIB_ERROR_TIMEOUT: 
            Serial.println("Time out error"); 
            break;
      default: 
            Serial.println("Unknown error"); 
            break;
     }
    temperature = (float)DHT11.temperature;
    //Serial.println(temperature);
    xQueueSend(xQueue1, &temperature, portMAX_DELAY);
    
    //Serial.print("Humidity (%): ");
    //Serial.println((float)DHT11.humidity, 2);
    //Serial.print("Temperature (oC): ");
    //Serial.println((float)DHT11.temperature, 2);
    //Serial.print("Temperature (oF): ");
    //Serial.println(Fahrenheit(DHT11.temperature), 2);
    //Serial.print("Temperature (K): ");
    //Serial.println(Kelvin(DHT11.temperature), 2);
    //Serial.print("Dew Point (oC): ");
    //Serial.println(dewPoint(DHT11.temperature, DHT11.humidity));
    //Serial.print("Dew PointFast (oC): ");
    //Serial.println(dewPointFast(DHT11.temperature, DHT11.humidity));
    delay(1000);
  }
}
void taskConform(void *pvParameters){
  float t;
  for(;;){
    if(xQueueReceive(xQueue1, &t, portMAX_DELAY)==pdPASS){
      Serial.println(t);
      xQueueSend(xQueue2, &t, portMAX_DELAY);
    }
  }
}
void taskBuzz(void *pvParameters){
  float t;
  for(;;){
    if(xQueueReceive(xQueue2, &t, portMAX_DELAY)==pdPASS){
      //Serial.println(t);
      if(t >= 32){
        int w = digitalRead(BUZZPIN);
        digitalWrite(BUZZPIN,!w);
      }
      else{
        digitalWrite(BUZZPIN,HIGH);
      }
    }
    delay(1000);
  }
}
