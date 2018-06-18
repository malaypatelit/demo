# demo
Spring Rest Api for demo for Paysafe server monitoring start and stop


Below are the Steps to run the rest Api and the touch point URLs for it.
1. Checkout the Git project and import into STS Suite.
2. Build the project and execute it as Spring Boot Application.
3. PaySafe API monitoring can be started using below URL from Postman or any other Rest URL Test Client with POST method.
  - http://localhost:8080/server/status?interval=2000&url=https://api.test.paysafe.com/accountmanagement/monitor
  - interval is the interval in milliseconds
  - url is the value of server URL for monitoring the Pay Safe API server. Test URL used is https://api.test.paysafe.com/accountmanagement/monitor .
  
4. Below URL can be used to monitor STOP with POST method:
  - http://localhost:8080/server/status?interval=2000&url=https://api.test.paysafe.com/accountmanagement/monitor
  When the same URL is received back it will stop monitoring the server. It compares whether it has been monitoring a server then it would stop that. If not than start that.

5. To get the results of the status of the monitoring it can be retrieved using GET method:
  - http://localhost:8080/server/result
  Results requirement seems to be not much clear it has been just tested as per understanding.
  
TestCases have not been implemented yet.
This REST API demo application will be shortly updated with the Test Cases.
