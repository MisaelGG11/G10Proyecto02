package com.example.couponsapp.email;

public class Message {

    public String mensaje(String usuario){
        String mensaje = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Registro</title>\n" +
                "    <style type=\"text/css\">\n" +
                "        .logo { text-align: center; padding: 30px;}\n" +
                "        .mensaje {text-align: center; padding: 30px;}\n" +
                "        .bienvenida {color: black;}\n" +
                "        .usuario {color:blue;}\n" +
                "        .informacion { text-align: center; color: green; font-weight: bold; font-size: medium; font-family: Georgia, 'Times New Roman', Times, serif;}\n" +
                "      </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"logo\">\n" +
                "        <img src=\"https://i.ibb.co/5547RQK/Recurso-4.png\" alt=\"Logo\" border=\"0\">\n" +
                "    </div>\n" +
                "    <div class=\"mensaje\">\n" +
                "        <h1 class=\"bienvenida\">Te damos la bienvenida</h1>\n" +
                "        <h2 class=\"usuario\">"+usuario+"</h2>\n" +
                "    </div>\n" +
                "    <div class=\"informacion\">\n" +
                "        <p>\n" +
                "            En nuestra aplicación, puedes buscar diferentes cupones que los restaurantes ponen a disposición para su uso.\n" +
                "        </p>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return mensaje;
    }
}
