Build keretrendszernek a Gradle-t választottuk kts nyelvvel. 
A nem megszokott fájl struktúra miatt voltak kisebb nehézségek, de ezeket sikerült abszolválni. 
- A projektre ráhúztunk egy gradle build-et
- A UI teszteket kiszedtük a build testjei közül, mert ez CI-on nem tud lefutni
- A Cucumber teszteket hozzáadtuk a build test task-jához. 
- Felvettük a sonarqube task-ot, ami segít elemzéseket futtatni.
- Ezek segítségével összeraktuk a CI pipeline-t. 
