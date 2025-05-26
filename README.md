<h1>Docs & protocols management system</h1>

Lightweight backend project for managing documents and protocols.

<h3>What do you need for run</h3>

- Installed Java 21 (including JRE)
- Docker desktop
- IDE (Looking into the project)
- SQL Tool (DBeaver)
- OPTIONAL - Maven 3.8.7

<h3>How to run it?</h3>

1. Clone project from github
2. Open **docker desktop**
3. In project root directory, open command prompt and type:
<code>mvnw.cmd clean install</code>. This will build
the whole project and create executable jar file
4. Type to command prompt: <code>docker-compose up -d</code>.
This will set up local database on your docker desktop.
5. Create new connection on your SQL tool and type there credentials
from <code>docker-compose.yml</code> file
6. After successful build, go to target directory
**docs-protocols-main\target**, open command prompt and type
<code>java -jar docs-protocols-main-1.0-SNAPSHOT.jar</code>.
Project will start.
7. Enjoy!