# CerHorProgress
A subclass of {@link android.view.View} class for creating a custom circular, horizontal and vertical progressBar with the possibility of two texts inside on circular progress. Such as the value of progression ...

# How to use CerHorProgress ?

## 1. Add the JitPack repository to your build file
Add it in your root ***build.gradle*** at the end of repositories:

```gradle
allprojects {
   repositories {
      maven { url 'https://jitpack.io' }
   }
}
```

## 2. Add the dependency

```gradle
dependencies {
   implementation 'com.github.ADOUJEAN:CerHorProgress:0.1.1'
}
```

## 3. Add this view in your activity layout ***activity_main.xml***
For use ***cercle progress*** set this :

```xml
<ci.jjk.cerhorprogress.CerHorProgress
        android:id="@+id/cercleProgress"
        app:progressType="cercle"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:progress="35"
        app:progressMax="100"
        app:progressMin="0"
        app:progressCercleText="35"
        app:progressCercleTextUnity="Mb/s"
        app:progressForgroundColor="#FF3700B3"
        app:progressBackgroundColor="@android:color/darker_gray"
        app:progressThickness="5dp"
        app:useAdjustColor="false"
        />
```

For use ***horizontal progress*** set this :

```xml
 <ci.jjk.cerhorprogress.CerHorProgress
        android:id="@+id/horizontalProgress"
        app:progressType="horizontal"
        android:layout_width="200dp"
        android:layout_height="30dp"
        app:progress="35"
        app:progressMax="100"
        app:progressMin="0"
        app:progressForgroundColor="#FF3700B3"
        app:progressBackgroundColor="@android:color/darker_gray"
        app:progressThickness="5dp"
        app:useAdjustColor="false"
        />
```

For use ***vertical progress*** set this :

```xml
 <ci.jjk.cerhorprogress.CerHorProgress
        android:id="@+id/verticalProgress"
        app:progressType="vertical"
        android:layout_width="50dp"
        android:layout_height="200dp"
        app:progress="35"
        app:progressMax="100"
        app:progressMin="0"
        app:progressForgroundColor="@android:color/darker_gray"
        app:progressBackgroundColor="#FF3700B3"
        app:progressThickness="5dp"
        app:useAdjustColor="false"
        />
```


## 4. Now you can dance with the CerHorProgress as you want :dancer: !

```kotlin
//Here is an example of use.
//Place this in your principle activity, in the block of an action. In my example I used a button

val maxValue=100
val minValue=1
val progresVal=((Math.random() * (maxValue -minValue))-minValue).toFloat()
// Anim cercle progress
cercleProgress.setProgressWithAnimationAndMax(progresVal,maxValue.toFloat())
// Anim horizontal progress
horizontalProgress.setProgressWithAnimationAndMax(progresVal,maxValue.toFloat())
// Anim vertical progress
verticalProgress.setProgressWithAnimationAndMax(progresVal,maxValue.toFloat())
```

That's it.

![ScreenShot](/Screen_Recording_20210611-103241_CerHorProgress_1.gif)

Thank you!
