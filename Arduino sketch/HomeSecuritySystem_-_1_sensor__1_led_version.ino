/* This program currently controls both ultrasonic sensors as well as the led */

#define echoPin1 11
#define trigPin1 10
#define echoPin2 13
#define trigPin2 12
#define led 9
 
long duration, distance,outerSensor,innerSensor;
 
void setup()
{
  Serial.begin (9600);
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
  pinMode(led, OUTPUT);
}
 
void loop() {
  SonarSensor(trigPin1, echoPin1);
  outerSensor = distance;
  SonarSensor(trigPin2, echoPin2);
  innerSensor = distance;
  ledOn(innerSensor);
  delay(300);

  if(outerSensor>1000){
    
  }
  else{
  Serial.println(outerSensor);
  }
}

//this function delays the bursts for 10 microseconds to ensure maximum output from sensor.
void SonarSensor(int trigPin,int echoPin)
{
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);

  //convert to centimeters.
  distance = (duration/2) / 29.1; 
}

void ledOn(long distance){
  
  if(distance<40){
    Serial.println(10000);
    digitalWrite(led, HIGH);
  }
  else{
    digitalWrite(led, LOW);
   }
 }


