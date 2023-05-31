A UI tesztek írásához az **AssertJ** library-t alkalmaztuk, mivel ezt a könyvtárt találtuk, amely támogatja a Swing-es komponensek tesztelését.

Szerettünk volna state-of-the-art tesztelési szoftvert alkalmazni, viszont nem találtunk egy egyértelműen piacvezető szoftvert, amely támogatja a Swing-et, így az AssertJ-nél maradt a csapat.

Tesztek írása közben, nehézséget jelentett az egyes komponensek megatalálására a kódban. Ahhoz, hogy ez rugalmasabban menjen egyedi azonosítókkal ruháztuk fel azokat a komponenseket, amelyeket teszteni szerettünk volna. Ennek köszönhetően, az azonosító által tudtunk hivatkozni arra az elemre, így tisztább és olvashatóbb kód született.

Problémákba ütköztünk a CI-vel kapcsolatban, mivel githubon futott az ellenőrzés, ahol nem megjeleníthető a UI teszt, így ott nem tudtuk őket futtatni. Megoldásként arra jutottunk, hogy a UI teszteket, csak lokálisan futtatjuk.