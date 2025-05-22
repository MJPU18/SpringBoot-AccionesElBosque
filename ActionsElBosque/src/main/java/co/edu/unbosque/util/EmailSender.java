package co.edu.unbosque.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
	
    public static void sendEmail(String toEmail, String subject, String content) {
        final String username = "jpbotmjpu@gmail.com";
        final String password = "ljfw pnna nlpm qgwq"; 
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true"); 
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465"); 
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "Acciones ElBosque"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String buildWelcomeEmail(String userName) {
        return """
            <div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>
                <div style='max-width: 600px; margin: auto; background: #fff; border-radius: 10px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>
                    <div style='background-color: #264653; padding: 20px; text-align: center; color: white;'>
                        <h1>🎉 ¡Bienvenido a Acciones ElBosque!</h1>
                    </div>
                    <div style='padding: 30px;'>
                        <p>Hola <strong>%s</strong>,</p>
                        <p>Te damos la bienvenida a nuestra plataforma de trading. Estamos encantados de que hayas decidido unirte a nuestra comunidad.</p>
                        
                        <p>A partir de ahora, podrás:</p>
                        <ul style='line-height: 1.6;'>
                            <li>📊 Operar en mercados internacionales en tiempo real</li>
                            <li>🔔 Recibir notificaciones personalizadas de tus operaciones</li>
                            <li>🔐 Mantener la seguridad de tu cuenta con autenticación robusta</li>
                        </ul>

                        <p style='margin-top: 20px;'>Puedes iniciar sesión en cualquier momento desde nuestra app o plataforma web:</p>
                        
                        <div style='text-align: center; margin: 30px 0;'>
                            <a href='http://localhost:4200/login' style='background-color: #2a9d8f; color: white; padding: 12px 24px; border-radius: 6px; text-decoration: none; font-weight: bold;'>Iniciar sesión</a>
                        </div>

                        <p>Si tienes alguna duda o necesitas soporte, no dudes en contactarnos.</p>
                        <p>¡Gracias por confiar en nosotros!</p>
                        <p style='margin-top: 30px;'>Equipo Acciones ElBosque</p>
                    </div>
                    <div style='background-color: #e9ecef; padding: 15px; text-align: center; font-size: 12px; color: #555;'>
                        © 2025 Acciones ElBosque. Todos los derechos reservados.<br>
                        Este es un mensaje automático, por favor no respondas a este correo.
                    </div>
                </div>
            </div>
        """.formatted(userName);
    }
    
    public static String buildOrderConfirmationEmail(String userName, String side, String type, String symbol, String quantity) {
        String action = side.equalsIgnoreCase("buy") ? "compra" : "venta";
        String color = side.equalsIgnoreCase("buy") ? "#2a9d8f" : "#e76f51";
        String emoji = side.equalsIgnoreCase("buy") ? "🟢" : "🔴";

        return """
            <div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>
                <div style='max-width: 600px; margin: auto; background: #fff; border-radius: 10px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>
                    <div style='background-color: %s; padding: 20px; text-align: center; color: white;'>
                        <h2>%s Confirmación de %s</h2>
                    </div>
                    <div style='padding: 30px;'>
                        <p>Hola <strong>%s</strong>,</p>
                        <p>Se ha ejecutado correctamente una orden de <strong>%s</strong> en tu cuenta de Acciones ElBosque. Aquí están los detalles:</p>

                        <table style='width: 100%%; border-collapse: collapse; margin-top: 20px;'>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Tipo de orden</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>%s</td>
                            </tr>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Símbolo</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>%s</td>
                            </tr>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Cantidad</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>%s</td>
                            </tr>
                        </table>

                        <p style='margin-top: 30px;'>Gracias por operar con nosotros.</p>
                        <p>Equipo Acciones ElBosque</p>
                    </div>
                    <div style='background-color: #e9ecef; padding: 15px; text-align: center; font-size: 12px; color: #555;'>
                        © 2025 Acciones ElBosque. Todos los derechos reservados.
                    </div>
                </div>
            </div>
        """.formatted(color, emoji, action.toUpperCase(), userName, action, type, symbol, quantity);
    }

    public static String buildAchRelationshipEmail(String userName, String bankType, String bankNumber, String routingNumber) {
        return """
            <div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>
                <div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>
                    <div style='background-color: #264653; padding: 20px; text-align: center; color: white;'>
                        <h2>🔗 Relación ACH creada</h2>
                    </div>
                    <div style='padding: 30px;'>
                        <p>Hola <strong>%s</strong>,</p>
                        <p>Tu cuenta bancaria ha sido vinculada correctamente con tu cuenta de trading.</p>

                        <table style='width: 100%%; border-collapse: collapse; margin-top: 20px;'>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Tipo de cuenta</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>%s</td>
                            </tr>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Número de cuenta</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>••••%s</td>
                            </tr>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Número de ruta</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>%s</td>
                            </tr>
                        </table>

                        <p style='margin-top: 20px;'>Ahora puedes realizar transferencias entre tu cuenta bancaria y la plataforma.</p>
                        <p>Gracias por usar Acciones ElBosque.</p>
                    </div>
                    <div style='background-color: #e9ecef; padding: 15px; text-align: center; font-size: 12px; color: #555;'>
                        © 2025 Acciones ElBosque. Este es un mensaje automático, no responder.
                    </div>
                </div>
            </div>
        """.formatted(userName, bankType, bankNumber.substring(bankNumber.length() - 4), routingNumber);
    }

    public static String buildAchTransferEmail(String userName, String transferType, String amount, String direction) {
        String dirText = direction.equalsIgnoreCase("INCOMING") ? "a tu cuenta de trading" : "desde tu cuenta de trading";
        String emoji = direction.equalsIgnoreCase("INCOMING") ? "💰" : "📤";
        String color = direction.equalsIgnoreCase("INCOMING") ? "#2a9d8f" : "#e76f51";

        return """
            <div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>
                <div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>
                    <div style='background-color: %s; padding: 20px; text-align: center; color: white;'>
                        <h2>%s Transferencia ACH realizada</h2>
                    </div>
                    <div style='padding: 30px;'>
                        <p>Hola <strong>%s</strong>,</p>
                        <p>Se ha completado una transferencia <strong>%s</strong> %s.</p>

                        <table style='width: 100%%; border-collapse: collapse; margin-top: 20px;'>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Tipo de transferencia</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>%s</td>
                            </tr>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Monto</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>$%s USD</td>
                            </tr>
                            <tr>
                                <td style='padding: 10px; border: 1px solid #ddd;'>Dirección</td>
                                <td style='padding: 10px; border: 1px solid #ddd;'>%s</td>
                            </tr>
                        </table>

                        <p style='margin-top: 20px;'>Puedes revisar el estado en tu historial de transferencias.</p>
                        <p>Equipo Acciones ElBosque</p>
                    </div>
                    <div style='background-color: #e9ecef; padding: 15px; text-align: center; font-size: 12px; color: #555;'>
                        © 2025 Acciones ElBosque. Este es un mensaje automático, no responder.
                    </div>
                </div>
            </div>
        """.formatted(color, emoji, userName, transferType, dirText, transferType, amount, direction);
    }

}
