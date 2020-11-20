Bitwig MPD 24 script extension
=============================

Early days. I want to integrate my workflow closer with the mpd, 
and I'd  also like to learn the bitwig api.

Experiments abound. 

### Developing
Dependencies
- Java 1.8 or newer
- Set the environment variable `BITWIG_EXTENSIONS_LOCATION` to the location bitwig expects the
extensions to be installed. On Windows, by default, this is `~/Documents/Bitwig\ Studio/Extensions`.

To deploy the extension to Bitwig run the following command:
- `./gradlew <sub-project>:install` for example `./gradlew um-one:install`
