# demo
Spring Rest Api for demo for Paysafe server monitoring start and stop


Below are the Steps to run the rest Api and the touch point URLs for it.
1. Checkout the Git project and import into STS Suite.
2. Build the project and execute it as Spring Boot Application.
3. PaySafe API monitoring can be started using below URL from Postman or any other Rest URL Test Client with POST method.
  - http://localhost:8080/server/status?interval=2000&url=start
  - interval is the interval in milliseconds
  - url is the value whether to START or STOP monitoring the Pay Safe API server.
  
4. Below URL can be used to monitor STOP with POST method:
  - http://localhost:8080/server/status?interval=2000&url=stop

5. To get the results of the status of the monitoring it can be retrieved using GET method:
  - http://localhost:8080/server/result
  
TestCases have not been implemented yet.
This REST API demo application will be shortly updated with the Test Cases.
