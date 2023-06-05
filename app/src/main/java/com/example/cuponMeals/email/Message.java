package com.example.cuponMeals.email;

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
                "        .usuario {color:green;}\n" +
                "        .informacion { text-align: center; color: gray; font-weight: bold; font-size: medium; font-family: Georgia, 'Times New Roman', Times, serif;}\n" +
                "      </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"logo\">\n" +

                "        <img src=\"https://i.ibb.co/ZdxMD0L/logo-Grupo10.jpg\" alt=\"Logo\" border=\"0\">\n" +
                "    </div>\n" +
                "    <div class=\"mensaje\">\n" +
                "        <h1 class=\"bienvenida\">Te deceamos una agradable bienvenida</h1>\n" +
                "        <h2 class=\"usuario\">"+usuario+"</h2>\n" +
                "    </div>\n" +
                "    <div class=\"informacion\">\n" +
                "        <p>\n" +
                "            Con nosostros puedes obtener diferentes cupones que los restaurantes otorgen a sus clientes mas fieles.\n" +
                "        </p>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return mensaje;
    }
}
