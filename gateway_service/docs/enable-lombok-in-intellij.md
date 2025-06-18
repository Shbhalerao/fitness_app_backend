# Enabling Lombok Annotation Processing in IntelliJ IDEA

1. **Install Lombok Plugin**
   - Go to `File` > `Settings` > `Plugins`.
   - Search for "Lombok" and install the plugin.
   - Restart IntelliJ IDEA if prompted.

2. **Enable Annotation Processing**
   - Go to `File` > `Settings` > `Build, Execution, Deployment` > `Compiler` > `Annotation Processors`.
   - Check the box: `Enable annotation processing`.

3. **Rebuild the Project**
   - Go to `Build` > `Rebuild Project`.

After these steps, Lombok annotations like `@Slf4j` should work and generate the `log` field automatically.
