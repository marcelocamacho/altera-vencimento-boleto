import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * GeraBoletoCaixa
 */
public class GeraBoletoCaixa {
    // 10498.37048 51036.132044 00000.821454 2 98620000142652
    //  5.5.5.6.5.6.1.14 = 47

    static final LocalDate DATA_BASE = LocalDate.of(1997, Month.OCTOBER,07);
    private String linhaDigitavel;
  
    public GeraBoletoCaixa(String linhaDigitavel) {

        this.linhaDigitavel = linhaDigitavel
            .replace(".","")
            .replace(" ", "");

        System.out.println("Codigo sem pontos ou traços ("+this.linhaDigitavel.length()+" digitos): "+this.linhaDigitavel);
    }

    public String getCampo1(){
        return "Campo1";
    }
    public String getCampo2(){
        return "Campo2";
    }
    public String getCampo3(){
        return "Campo3";
    }
    public String getCampo4(){
        return "Campo4";
    }

    public String getCampo5(String linhaDigitavel){
        
        if(linhaDigitavel.length() != 47){
            return "Código inválido";
        }

        return linhaDigitavel.substring(33);
    }

    public float getValorDocumento(String campo5){
        String valorTxt = campo5.substring(4);
        float valorDocumento = Float.parseFloat(
            valorTxt)/100;
        return valorDocumento;
    }

    public String getVencimento(String campo5){
        String fatorVencimento = campo5.substring(0, 4);
        
        LocalDate vencimento = DATA_BASE.plusDays(Long.parseLong(fatorVencimento));
        
        return vencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
    }


    public String setNovoFatorVencimento(String novoVencimento){
        LocalDate novaData = LocalDate.parse(novoVencimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        long fatorVencimento = DATA_BASE.until(novaData,ChronoUnit.DAYS);

        return String.valueOf(fatorVencimento);

    }

    public static void main(String[] args) {


        GeraBoletoCaixa novoBoleto = new GeraBoletoCaixa(
        //    "10498.37048 51036.132044 00000.821454 2 9862 0000142652"
        "10498370485103613204400000821454298650000142652"
        );

        String campo5 = novoBoleto.getCampo5(
            novoBoleto.linhaDigitavel
         );

        float valor =  novoBoleto.getValorDocumento(campo5);

        String vencimento = novoBoleto.getVencimento(campo5);

        System.out.println("Valor: "+ String.valueOf(valor));
        
        System.out.println("Vencimeto: "+ vencimento);

        System.out.println("Novo Fator de Vencimento: "+ novoBoleto.setNovoFatorVencimento("11/10/2024"));
    }

}