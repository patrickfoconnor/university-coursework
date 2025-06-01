# Main class that will hold base Robot object

from enum import Enum
import serial
import sys
import time

# SERVO CONSTANTS
TARGET_CENTER = 6000
MAX_SERVO = 7600
MIN_SERVO = 4400
SERVO_INCREMENT = 225
SERVO_INCREMENT_WAIST = 750
INCREMENT = 10

# MOTOR CONSTANTS

MOTOR_SPEED = 6000

MOTOR_INCREMENT = 200
MOTOR_TARGET_RESET = 6000  # 6000


def getUSB():
    usb = None
    try:
        usb = serial.Serial('/dev/ttyACM0')
        print(usb.name)
        print(usb.baudrate)
    except:
        try:
            usb = serial.Serial('/dev/ttyACM1')
            print(usb.name)
            print(usb.baudrate)
        except:
            print("No servo serial ports found")
            sys.exit(0)
    return usb


class RobotMotor(Enum):
    WheelLeft = 0x01
    WheelRight = 0x02
    Waist = 0x00
    HeadX = 0x03
    HeadY = 0x04
    Shoulder = 0x05
    ArmRight = 0x06


class TangoRobot:
    # class properties
    usb = None
    win = None
    motors = 0
    speed = MOTOR_SPEED
    dummy = False

    # constructor
    def __init__(self):
        self.usb = getUSB()
        self.resetRobot()
        self.turnLeftSpeed = 7000
        self.turnRightSpeed = 5000

    # write out command to usb
    def writeCmd(self, motor, target):
        # Validate that 'motor' is of type 'RobotMotor' enum class
        if not isinstance(motor, RobotMotor):
            # Show error, motor is not of correct type
            print("Motor must be type {!s} - type '{!s}' not accepted".format(type(RobotMotor), type(motor)))
            return
        # Build command
        lsb = target & 0x7F
        msb = (target >> 7) & 0x7F
        command = chr(0xaa) + chr(0xC) + chr(0x04) + chr(motor.value) + chr(lsb) + chr(msb)
        # Check if usb is not None
        if self.usb is not None:
            # Write out command
            self.usb.write(command.encode('utf-8'))

    # Methods for Moving the robot waist
    def waistLeft(self):
        counter = 0
        while (counter <= SERVO_INCREMENT_WAIST):
            self.motors -= INCREMENT
            if (self.motors > MAX_SERVO):
                self.motors = MAX_SERVO
            self.writeCmd(RobotMotor.Waist, self.motors)
            counter += INCREMENT

    def waistRight(self):
        counter = 0
        while (counter <= SERVO_INCREMENT_WAIST):
            self.motors += INCREMENT
            if (self.motors < MIN_SERVO):
                self.motors = MIN_SERVO
            self.writeCmd(RobotMotor.Waist, self.motors)
            counter += INCREMENT

    # Methods for Moving the robot head
    def headDown(self):
        counter = 0
        while (counter <= SERVO_INCREMENT):
            self.motors += INCREMENT
            if (self.motors > MAX_SERVO):
                self.motors = MAX_SERVO
            self.writeCmd(RobotMotor.HeadY, self.motors)
            counter += INCREMENT

    def headUp(self):
        counter = 0
        while (counter <= SERVO_INCREMENT):
            self.motors -= INCREMENT
            if (self.motors < MIN_SERVO):
                self.motors = MIN_SERVO
            self.writeCmd(RobotMotor.HeadY, self.motors)
            counter += INCREMENT

    def headLeft(self):
        counter = 0
        while (counter <= SERVO_INCREMENT):
            self.motors += INCREMENT
            if (self.motors > MAX_SERVO):
                self.motors = MAX_SERVO
            self.writeCmd(RobotMotor.HeadX, self.motors)
            counter += INCREMENT

    def headRight(self):
        counter = 0
        while (counter <= SERVO_INCREMENT):
            self.motors -= INCREMENT
            if (self.motors < MIN_SERVO):
                self.motors = MIN_SERVO
            self.writeCmd(RobotMotor.HeadX, self.motors)
            counter += INCREMENT

    # Methods for driving the robot
    def driveForward(self):
        # self.resetMotor(self.motors)
        if self.dummy == False:
            self.resetMotor()
            self.dummy = True
        self.speed -= MOTOR_INCREMENT
        time.sleep(.4)
        if (self.speed < MIN_SERVO):
            self.speed = MIN_SERVO
            print("Too Speedy")
        self.writeCmd(RobotMotor.WheelLeft, 6500)
        # self.writeCmd(RobotMotor.WheelRight, self.speed)
        print(self.speed)


    def driveBackward(self):
        self.speed += MOTOR_INCREMENT
        time.sleep(.4)
        if (self.speed > MAX_SERVO):
            self.speed = MAX_SERVO
            print("Too Slow")
        self.writeCmd(RobotMotor.WheelLeft, self.speed)
        print(self.speed)

    def turnLeft(self):
        time.sleep(.4)

        self.writeCmd(RobotMotor.WheelRight, self.turnLeftSpeed)

        time.sleep(.5)
        self.resetWheels()
        print(self.turnLeftSpeed)

    def turnRight(self):
        time.sleep(.4)

        self.writeCmd(RobotMotor.WheelRight, self.turnRightSpeed)

        time.sleep(.5)
        self.resetWheels()
        print(self.turnRightSpeed)

    def adventureAttack(self):
        time.sleep(0.4)
        self.writeCmd(RobotMotor.Shoulder, 7500)
        time.sleep(0.4)
        self.writeCmd(RobotMotor.ArmRight, 7500)

        time.sleep(3)
        self.writeCmd(RobotMotor.Shoulder, 6000)
        time.sleep(0.4)
        self.writeCmd(RobotMotor.ArmRight, 6000)

    def resetMotor(self):
        if (self.motors > MOTOR_TARGET_RESET):
            self.motors = self.motors
        else:
            self.motors = MOTOR_TARGET_RESET

    def resetRobot(self):
        # Center all motors to 6000
        for motor in RobotMotor:
            self.writeCmd(motor, TARGET_CENTER)
            time.sleep(.5)

    def resetWheels(self):
        # Center all robot motors to 6000
        self.writeCmd(RobotMotor.WheelRight, TARGET_CENTER)
        self.writeCmd(RobotMotor.WheelLeft, TARGET_CENTER)

    def wheelMotorCycle(self):
        # ???
        time.sleep(.2)
        self.writeCmd(RobotMotor.WheelRight, MAX_SERVO)
        self.writeCmd(RobotMotor.WheelLeft, MAX_SERVO)
        self.writeCmd(RobotMotor.WheelRight, MIN_SERVO)
        self.writeCmd(RobotMotor.WheelLeft, MIN_SERVO)



robot = TangoRobot()
