package br.com.miller.muckup.domain;

public enum Status {

    sended("Enviado"), received("Recebido"), canceled("Cancelado"), news("Esperando");

    private String status;

    Status(String value) {
        status =  value;
    }


    public String getStatus(){
         return status;
    }
}
