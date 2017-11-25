# Android - AWS Mobile Hub - Custom Login Model
This is a simple login model for AWS Mobile Hub projects.

AWS Mobile Hub creates its own login screen and apparently you cannot modify the UI to match with your mobile app.

This model provides methods to control login functions such as sign in, sign up, confirm e-mail, forgot password, get user attributes, and more (described below).

You can pull this repository, which is a sample Android project, and test with your own AWS Mobile Hub project, or you can just copy the class `AWSLoginModel.java` and the interface `AWSLoginHandler.java`, both located in `app/src/main/java/com/wtmimura/awsandroid/aws/`.

I explained how I started this model in my website at [www.wtmimura.com](http://www.wtmimura.com). You can also find other stuff that might be helpful. Go there and take a look!

You can copy, change, or whatever. Just use some common sense and good faith.

If you use this, please leave a message at wotom.wtmimura@gmail.com. That'd be awesome!

## How to Use
1. Create an AWS Mobile Hub project.
2. Add **User Sign-in** service to the project.
    1. Choose **email** as sign-in method, and **no** multi-factor authentication.
3. Download the **configuration file**.
4. Download this repository.
5. Add the **configuration file** to this Android app (in a raw resource directory).
6. Run the app!

## Documentation
Documentation about the AWSLoginModel and AWSLoginHandler (both in JavaDoc):
http://wtmimura.com/android/documentations/JavaDoc/
