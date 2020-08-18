PixelEXPRates
==============================
Faça os pokémons dos jogadores ganharem EXP adicional **nas batalhas** com base no nível do pokémon.
É um conceito baseado em servidores de outros jogos, como Tibia.

Os comandos são:
- /rates - Visualiza as rates do servidor - Sem permissão necessária
- /rates reload - Recarrega a config do plugin - Permissão `pixelexprates.reload`

**Sinta-se livre para baixar, editar, republicar, redistribuir ou até mesmo vender uma edição deste plugin, não é necessário pedir permissão (basta ver a licença).**

Como este plugin funciona?
-------------------------------------------
O plugin cria uma configuração na pasta `./config/` contendo os rates do servidor. Por exemplo, aqui está uma configuração:
```
rates=[
    {
        expMultiplier=3.0
        minLevel=1
        maxLevel=30
    },
    {
        expMultiplier=2.0
        minLevel=31
        maxLevel=60
    },
    {
        expMultiplier=1.0
        minLevel=61
        maxLevel=100
    }
]
```

Isso significa que entre o nível 1 e 30, um pokémon ganhará 3x a EXP normal **em batalhas**. 

Entre o nível 31 a 60, um pokémon ganhará 2x a EXP normal **em batalhas**. 

Entre o nível 61 e 100, um pokémon ganhará a EXP normal (já que é apenas 1x) **em batalhas**.

O plugin afeta apenas batalhas.

Como transformar a source em .jar (caso não souber como usar o Gradle)
-------------------------------------------

- Tenha o Java Development Kit instalado em seu computador (talvez o Java Runtime Environment não seja suficiente);
- Abra um terminal (CMD, por exemplo);
- Navegue **o terminal** até a pasta raíz deste projeto (no Windows, usa-se o comando `cd`);
- Execute `gradlew build` e aguarde a finalização;
- Obtenha o arquivo .jar construído dentro da pasta `/build/libs/`

Como instalar as sources para editá-lo em sua IDE (em inglês)
-------------------------------------------

See the Forge Documentation online for more detailed instructions:
http://mcforge.readthedocs.io/en/latest/gettingstarted/

Step 1: Open your command-line and browse to the folder where you extracted the zip file.

Step 2: Once you have a command window up in the folder that the downloaded material was placed, type:

Windows: "gradlew setupDecompWorkspace"
Linux/Mac OS: "./gradlew setupDecompWorkspace"

Step 3: After all that finished, you're left with a choice.
For eclipse, run "gradlew eclipse" (./gradlew eclipse if you are on Mac/Linux)

If you prefer to use IntelliJ, steps are a little different.
1. Open IDEA, and import project.
2. Select your build.gradle file and have it import.
3. Once it's finished you must close IntelliJ and run the following command:

"gradlew genIntellijRuns" (./gradlew genIntellijRuns if you are on Mac/Linux)

Step 4: The final step is to open Eclipse and switch your workspace to /eclipse/ (if you use IDEA, it should automatically start on your project)

If at any point you are missing libraries in your IDE, or you've run into problems you can run "gradlew --refresh-dependencies" to refresh the local cache. "gradlew clean" to reset everything {this does not affect your code} and then start the processs again.

Should it still not work, 
Refer to #ForgeGradle on EsperNet for more information about the gradle environment.

Tip:
If you do not care about seeing Minecraft's source code you can replace "setupDecompWorkspace" with one of the following:
"setupDevWorkspace": Will patch, deobfuscate, and gather required assets to run minecraft, but will not generate human readable source code.
"setupCIWorkspace": Same as Dev but will not download any assets. This is useful in build servers as it is the fastest because it does the least work.

Tip:
When using Decomp workspace, the Minecraft source code is NOT added to your workspace in a editable way. Minecraft is treated like a normal Library. Sources are there for documentation and research purposes and usually can be accessed under the 'referenced libraries' section of your IDE.
