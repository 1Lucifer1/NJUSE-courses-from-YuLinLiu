const int buttonPin = 2; //接中断2号口(D2)，可以触发中断0
const int ledPin = A0;

int led = LOW;
int buttonState;
int volButton = LOW;
int longOnesCount = 0;
bool inOnes = false;
char str[1000];
int cur = 0;
unsigned long lastDebounceTime = 0;
unsigned long debounceDelay = 50;
unsigned long pressedTime = 0;
void setup() {
  memset(str,0,1000);
  pinMode(buttonPin, INPUT);
  //pinMode(3, OUTPUT);
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, led);
  //digitalWrite(buttonPin,0);
  attachInterrupt(0, changLight, CHANGE);
  //attachInterrupt(1, pressed, CHANGE);
  Serial.begin(9600);
}

void loop() {
  //do nothing
}

void changLight(){
  // read the state of the switch into a local variable:
  int reading = digitalRead(buttonPin);
  //Serial.print("vol:");
  //Serial.println(volButton);
  //Serial.print("reading:");
  //Serial.println(reading);
  if(reading!=volButton){
    if ((millis() - lastDebounceTime) > debounceDelay) {
    // whatever the reading is at, it's been there for longer than the debounce
    // delay, so take it as the actual current state:

    // if the button state has changed:
    if (reading != buttonState) {
      buttonState = reading;
      
      // only toggle the LED if the new button state is HIGH
      if (buttonState == HIGH) {
        // stable to high      
        //Serial.print("high");
        led = LOW;
        pressed();
        
      } else {
        // stable to low   
        //Serial.print("low");     
        led = HIGH;
        pressedTime = millis();
        pressed();
      }
    }
  }
  digitalWrite(ledPin, led);
  volButton = reading;
  lastDebounceTime = millis();
    
  }
  
  
  
}

void pressed() {
  if (led == HIGH) {
    //Serial.println("Pressed");
  } else {
    int elapse = millis() - pressedTime - debounceDelay;
    //Serial.println(elapse);
    if (elapse > 500) {
      str[cur++] = '1';
      if (!inOnes) {
        longOnesCount += 1;
        inOnes = true;
      }
    } else if (elapse <= 500) {
      str[cur++] = '0';
      inOnes = false;
    }
    if(str[1] == 0){
      str[0]='0';
      inOnes = false;
      longOnesCount--;
    }
    Serial.println(str);
    Serial.print("Count: ");
    Serial.println(longOnesCount);
    
    
    
    //Serial.print("Unpressed: ");
    //Serial.println(elapse);
  }
}
