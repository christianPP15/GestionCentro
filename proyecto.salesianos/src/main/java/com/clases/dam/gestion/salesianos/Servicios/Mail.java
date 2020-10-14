package com.clases.dam.gestion.salesianos.Servicios;

import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Properties;


public class Mail {

        private Properties properties;

        private  Session session;

        public Mail(String ruta) throws IOException {
            this.properties=new Properties();
            loadConfig(ruta);
            session = Session.getDefaultInstance(properties);
        }

        private void loadConfig(String ruta) throws IOException {
            InputStream is=new FileInputStream(ruta);
            this.properties.load(is);
            checkConfig();
        }

        private void checkConfig() throws InvalidParameterException{
            String [] keys={
                "mail.smtp.host",
                "mail.smtp.port",
                "mail.smtp.user",
                "mail.smtp.password",
                "mail.smtp.starttls.enable",
                "mail.smtp.auth"
            };

            for(int i=0;i<keys.length;i++){
                if(this.properties.get(keys[i])==null){
                    throw new InvalidParameterException("No existe la clave"+keys[i]);
                }
            }
        }
        public void enviarEmail(String asunto,String mensaje,String correo) throws MessagingException {
            MimeMessage contenedor = new MimeMessage(session);
            contenedor.setFrom(new InternetAddress((String) this.properties.get("mail.smtp.user")));
            contenedor.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            contenedor.setSubject(asunto);
            contenedor.setText(mensaje);
            Transport t = session.getTransport("smtp");
            t.connect((String) this.properties.get("mail.smtp.user"), (String) this.properties.get("mail.smtp.password"));
            t.sendMessage(contenedor, contenedor.getAllRecipients());
        }
}
