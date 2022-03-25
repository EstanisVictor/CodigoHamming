package transferenciadadostp;

public class Receptor {

    // mensagem recebida pelo transmissor
    private String mensagem;

    public Receptor() {
        // mensagem vazia no inicio da execução
        this.mensagem = "";
    }

    public String getMensagem() {
        return mensagem;
    }

    private void decodificarDado(boolean bits[]) {
        int codigoAscii = 0;
        int expoente = bits.length - 1;

        // converntendo os "bits" para valor inteiro para então encontrar o valor tabela
        // ASCII
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                codigoAscii += Math.pow(2, expoente);
            }
            expoente--;
        }

        // concatenando cada simbolo na mensagem original
        this.mensagem += (char) codigoAscii;
    }

    private void decoficarDadoHemming(boolean bits[]) {

        boolean vetParidade[] = new boolean[4];
        vetParidade[0] = bits[0] ^ bits[2] ^ bits[4] ^ bits[6] ^ bits[8] ^ bits[10];
        vetParidade[1] = bits[1] ^ bits[2] ^ bits[5] ^ bits[6] ^ bits[9] ^ bits[10];
        vetParidade[2] = bits[3] ^ bits[4] ^ bits[5] ^ bits[6] ^ bits[11];
        vetParidade[3] = bits[7] ^ bits[8] ^ bits[9] ^ bits[10] ^ bits[11];

        // //implemente a decodificação Hemming aqui e encontre os
        // //erros e faça as devidas correções para ter a imagem correta
        // boolean mensagemCerta[] = new boolean[8];

        int indice = 0, potencia=3;
        for (int i = (vetParidade.length-1); i >= 0; i--) {
            if(vetParidade[i]){
                indice+=Math.pow(2, potencia);
            }
            potencia--;
        }

        if(!(indice-1==-1)){
            for (int i = 0; i < bits.length; i++) {
                if (i != 0 && i == indice - 1) {
                    bits[i] = !(bits[i]);
                }
            }
        }
        
        boolean mensagemCerta[] = new boolean[8];

        mensagemCerta[0] = bits[2];
        mensagemCerta[1] = bits[4];
        mensagemCerta[2] = bits[5];
        mensagemCerta[3] = bits[6];
        mensagemCerta[4] = bits[8];
        mensagemCerta[5] = bits[9];
        mensagemCerta[6] = bits[10];
        mensagemCerta[7] = bits[11];

        
        // trasformando em mesagem novamente
        decodificarDado(mensagemCerta);
    }

    // recebe os dados do transmissor
    public void receberDadoBits(boolean bits[]) {

        // aqui você deve trocar o médodo decofificarDado para decoficarDadoHemming
        // (implemente!!)
        decoficarDadoHemming(bits);
    }
}
