# Java MPEG 1/2/2.5 Layer 1/2/3 Decoder
This is a fork of [JLayer](https://web.archive.org/web/20210108055829/http://www.javazoom.net/javalayer/javalayer.html) and [MP3SPI](https://web.archive.org/web/20200624143314/http://www.javazoom.net/mp3spi/mp3spi.html).

This library contains a decoder that decodes/converts MPEG 1/2/2.5 Layer 1/2/3 (i.e. MP3) in real time for the JAVA(tm) platform, and an SPI (Service Provider Interface) that adds MP3 support for JavaSound. This is a non-commercial project and anyone can add the contribution.

**KNOWN PROBLEM:**  
99% of MP3 plays well with this library but some (1%) return an ArrayIndexOutOfBoundsException while playing. It might come from invalid audio frames.  
Workaround : Just try/catch ArrayIndexOutOfBoundsException in your code to skip non-detected invalid frames.

## Add the library to your project (gradle)
1. Add the Maven Central repository (if not exist) to your build file:
```groovy
repositories {
    ...
    mavenCentral()
}
```

2. Add the dependency:
```groovy
dependencies {
    ...
    implementation 'com.tianscar.javasound:javasound-mp3:1.9.6'
}
```

## Usage
[Tests and Examples](/src/test/java/javazoom/jl/test)  
[MP3 Player](/src/test/java/javazoom/jl/player)  
[Converter CLI](/src/test/java/javazoom/jl/converter)

Note you need to download test audios [here](https://github.com/Tianscar/fbodemo1) and put them to /src/test/java/resources to run the test code properly!

## License
[LGPL-2.1](/LICENSE)
