import pyttsx3

engine = pyttsx3.init()

voice_num = 2
text_to_say = "Hello Robot, Class! I am Tango!"

voices = engine.getProperty('voices')
engine.setProperty('voice', voices[voice_num].id)

engine.say(text_to_say)
engine.runAndWait()
