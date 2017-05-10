# Example customer survey functionality using Rescue SDK

If the survey is configured in the admin center, then the DisconnectedEvent will contain the url.
Get it using DisconnectedEvent.getCustomerSurveyUrl():

```java
@Subscribe
public void on(DisconnectedEvent e) {
    addMessage("Disconnected");
    addMessage("Survey url: "+e.getCustomerSurveyUrl());
}
```

See: [MainActivity.java](https://github.com/balazsbanyai/customerSurveyExample/blob/master/app/src/main/java/com/logmein/customersurveywithrescuesdk/MainActivity.java#L68)
