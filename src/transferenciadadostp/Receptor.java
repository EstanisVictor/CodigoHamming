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

        //Operações em XOR
        boolean vetParidade[] = new boolean[4];
        vetParidade[0] = bits[0] ^ bits[2] ^ bits[4] ^ bits[6] ^ bits[8] ^ bits[10];
        vetParidade[1] = bits[1] ^ bits[2] ^ bits[5] ^ bits[6] ^ bits[9] ^ bits[10];
        vetParidade[2] = bits[3] ^ bits[4] ^ bits[5] ^ bits[6] ^ bits[11];
        vetParidade[3] = bits[7] ^ bits[8] ^ bits[9] ^ bits[10] ^ bits[11];
        
        /*
        Verificando e retornado o indice referente ao tratamento de erro segundo a teoria de hamming
        A verificação é feita usando como potencia de base de 2.
        */
        int indice = 0, potencia=3;
        for (int i = (vetParidade.length-1); i >= 0; i--) {
            if(vetParidade[i]){
                indice+=Math.pow(2, potencia);
            }
            potencia--;
        }
        
        /*
        Verificando se o resultado do indice de tratamento de erro é -1
        se for, quer dizer que não houve ruido, caso contrario será corrigido 
        */
        if(!(indice-1==-1)){
            for (int i = 0; i < bits.length; i++) {
                if (i == indice - 1) {
                    bits[i] = !(bits[i]);
                    break;
                }
            }
        }
        
        boolean mensagemCerta[] = new boolean[8];
        int indiceCodigoHamming=2;

        /*
        Convertendo o vetor que tem os bits da mensnagem com o codigo de hamming,
        para um vetor com a mensagem original sem ruídos
        */
        for (int indiceMensagemCerta = 0; indiceMensagemCerta < mensagemCerta.length; indiceMensagemCerta++) {
            if(indiceCodigoHamming != 3 && indiceCodigoHamming != 7){
                mensagemCerta[indiceMensagemCerta] = bits[indiceCodigoHamming];
            }else{
                //Intuito de perder uma posição, já que o bits de hamming tem 12 posições
                indiceMensagemCerta--;
            }
            indiceCodigoHamming++;
        }
        
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
