# Main class that will hold base Robot object

from enum import Enum
import serial
import sys
import time
import maestro

# SERVO CONSTANTS
TARGET_CENTER = 6000
MAX_SERVO = 7500
MIN_SERVO = 4400
SERVO_INCREMENT = 1500
INCREMENT = 10

# MOTOR CONSTANTS

MOTOR_NEUTRAL = 6000
MOTOR_MAX_INTERVAL = 500
ROCK_CRAWL = 6420





def getUSB():
    usb = None
    try:
        usb = maestro.Controller("/dev/ttyACM0")
    except:
        try:
            usb = maestro.Controller("/dev/ttyACM1")
        except:
            print("No servo serial ports found")
            sys.exit(0)
    return usb


class RobotMotor(Enum):
    WheelMotor = 0x00
    SteeringServo = 0x01
    WinchServo = 0x02
    LightBar = 0x03


class Rover:
    # class properties
    usb = None
    win = None
    motors = 0
    dummy = False
    speed = 6000
    # Steering constants
    direction = "straight"

    # constructor
    def __init__(self):
        self.usb = getUSB()
        # adjustTrim(self)

    def sendCmd(self, cmd):
        cmdStr = chr(0xaa) + chr(0x0c) + cmd
        self.usb.write(bytes(cmdStr, 'latin-1'))

    def setTarget(self, chan, target):
        lsb = target & 0x7f  # 7 bits for least significant byte
        msb = (target >> 7) & 0x7f  # shift 7 and take next 7 bits for msb
        cmd = chr(0x04) + chr(chan) + chr(lsb) + chr(msb)
        self.sendCmd(cmd)

    # Methods for driving the robot
    def driveForward(self, controllerValue):
        targetSpeed = int(((controllerValue/100) * MOTOR_MAX_INTERVAL) + TARGET_CENTER)
        rover.speed = targetSpeed
        self.usb.setTarget(0x00, targetSpeed)

    def driveNeutral(self):
        # Going from forward to neutral
        if rover.speed > 6000:
            while (rover.speed > 6000):
                rover.speed -= 50
                self.usb.setTarget(0x00, rover.speed)
        # Going from reverse to neutral
        elif rover.speed < 6000:
            while (rover.speed < 6000):
                rover.speed += 50
                self.usb.setTarget(0x00, rover.speed)
        else:
            self.usb.setTarget(0x00, 6000)

    def driveBackward(self, controllerValue):
        targetSpeed =  int(TARGET_CENTER - ((controllerValue/100) * MOTOR_MAX_INTERVAL))
        rover.speed = targetSpeed
        self.usb.setTarget(0x00, targetSpeed)

    def rockCrawl(self):
        rover.speed = ROCK_CRAWL
        self.usb.setTarget(0x00, ROCK_CRAWL)

    # Control the steering servos
    def turnLeft(self, target_angle):
        if self.direction != "left":
            self.usb.setTarget(0x01, 6000)
            self.direction = "left"
        else:
            pass
        time.sleep(.5)

    def turnRight(self, target_angle):
        if self.direction != "right":
            self.usb.setTarget(0x01, 6750)
            self.direction = "right"
        else:
            pass
            #self.direction = "right"
        time.sleep(.5)

    def centerSteering(self):
        if self.direction != "straight":
            self.usb.setTarget(0x01, 6250)
            self.direction = "straight"
        else:
            pass
        time.sleep(.5)

    # Control the steering servos
    def winchReverse(self):
        self.usb.setTarget(0x02, 6500)
        time.sleep(.5)

    def winchForward(self):
        self.usb.setTarget(0x02, 5500)
        time.sleep(.5)

    def winchNeutral(self):
        self.usb.setTarget(0x02, 6000)
        time.sleep(.5)

    def initThrottle(self, inputString):
        # Send neutral signal before powering
        # Nuetral trigger position replication
        if (inputString == "n"):
            print("Going to nuetral")
            self.usb.setTarget(0x00, 5984)

        # Full Forward trigger position replication
        elif (inputString == "f"):
            print("Going full forward")
            self.usb.setTarget(0x00, 8000)

        # Full Reverse trigger position replication
        elif (inputString == "r"):
            print("Going to full reverse")
            self.usb.setTarget(0x00, 4000)
        # Full Reverse trigger position replication
        elif (inputString == "sf"):
            print("Going to soft forward")
            self.usb.setTarget(0x00, 6500)

        else:
            pass


rover = Rover()

print("What step of initialization in order(n, f, r, n, x) \n" +
      " 1) Neutral Before plugging ESC to Battery \n" +
      " 2) One red flash - Full forward \n" +
      " 3) Two red flashes - Full Reverse \n" +
      " 4) Green flashing - Neutral \n")
while True:
    initKey = raw_input("What step of initialization: ")
    if initKey == "x":
        break
    else:
        rover.initThrottle(initKey)


