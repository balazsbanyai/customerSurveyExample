package com.logmein.customersurveywithrescuesdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.logmein.rescuesdk.api.eventbus.Subscribe;
import com.logmein.rescuesdk.api.session.Session;
import com.logmein.rescuesdk.api.session.SessionFactory;
import com.logmein.rescuesdk.api.session.config.SessionConfig;
import com.logmein.rescuesdk.api.session.event.ConnectedEvent;
import com.logmein.rescuesdk.api.session.event.ConnectionErrorEvent;
import com.logmein.rescuesdk.api.session.event.DisconnectedEvent;
import com.logmein.rescuesdk.api.session.event.DisconnectingEvent;
import com.logmein.rescuesdk.api.session.event.WaitingForTechnicianEvent;

public class MainActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static String API_KEY = "8ZgeNOIaHsnUcxXJgpwglMlGkdaBfs6dObPFJ6Po";

    public void connect(View view) {

        SessionFactory.newInstance().create(this, API_KEY, new SessionFactory.SessionCreationCallback() {
            @Override
            public void onSessionCreated(Session session) {
                session.getEventBus().add(MainActivity.this);

                session.connect(SessionConfig.createWithChannelId("1749043564"));
                MainActivity.this.session = session;
            }
        });
    }

    @Subscribe
    public void on(ConnectionErrorEvent e) {
        addMessage("Error");
    }

    @Subscribe
    public void on(WaitingForTechnicianEvent e) {
        addMessage("WaitingForTechnician");
    }

    @Subscribe
    public void on(ConnectedEvent e) {
        session.disconnect();

        addMessage("Connected");
    }

    @Subscribe
    public void on(DisconnectingEvent e) {
        addMessage("Disconnecting: "+e.getClass().getSimpleName());
    }

    @Subscribe
    public void on(DisconnectedEvent e) {
        addMessage("Disconnected");
        addMessage("Survey url: "+e.getCustomerSurveyUrl());
    }

    private void addMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
