package br.com.abacelli.calculatorigti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.abacelli.calculatorigti.extentions.roundTwoDigits
import br.com.abacelli.calculatorigti.objects.InssFaixas
import br.com.abacelli.calculatorigti.objects.IrrfFaixas
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCalcular.setOnClickListener{
            val salarioBruto = txtResultSalario.text.toString().toDouble();
            val numDependentes = txtNumDep.text.toString().toDouble();
            val outrosDescontos = txtNumDesc.text.toString().toDouble();

            val resultInss = calculoINSS(salarioBruto);
            val resultIrrf = calculoIRRF(salarioBruto, numDependentes);
            val salarioLiquido = calculoSalarioLiquido(salarioBruto, resultInss, resultIrrf, outrosDescontos);
            val porcentagemDescontos = calculoPorcentagemDescontos(salarioBruto, resultInss, resultIrrf, outrosDescontos);

            val moveIntent = Intent(this, ResultActivity::class.java);
            moveIntent.putExtra("INSS", resultInss);
            moveIntent.putExtra("IRRF", resultIrrf);
            moveIntent.putExtra("DESCONTOS", outrosDescontos);
            moveIntent.putExtra("SALARIOBRUTO", salarioBruto);
            moveIntent.putExtra("SALARIOLIQUIDO", salarioLiquido);
            moveIntent.putExtra("PORCENTAGEMDESCONTOS", porcentagemDescontos);

            startActivity(moveIntent);
            finishAffinity();

        }

    }

    private fun calculoINSS(salarioBruto: Double): Double{

        var resultInss = 0.0;

        if(salarioBruto <= 1045){
            resultInss = (salarioBruto * InssFaixas.INSS_FAIXA1);
        }
        if(salarioBruto > 1045 && salarioBruto <= 2089.60) {
            resultInss = (salarioBruto * InssFaixas.INSS_FAIXA2) - InssFaixas.DEDUCAO_INSS2;
        }
        if(salarioBruto > 2089.60 && salarioBruto <= 3134.40) {
            resultInss = (salarioBruto * InssFaixas.INSS_FAIXA3) - InssFaixas.DEDUCAO_INSS3;

        }
        if(salarioBruto > 3134.40 && salarioBruto <= 6101.06) {
            resultInss = (salarioBruto * InssFaixas.INSS_FAIXA4) - InssFaixas.DEDUCAO_INSS4;
        }
        if(salarioBruto > 6101.06) {
            resultInss = InssFaixas.DEDUCAO_INSS5;
        }
        return resultInss.roundTwoDigits();
    }

    private fun calculoIRRF(salarioBruto: Double, numDependentes: Double): Double {

        val resultInss = (salarioBruto - calculoINSS(salarioBruto)) - (numDependentes * 189.59);
        var resultIrrf = 0.0;

        if(resultInss <= 1903.98){
            resultIrrf = 0.0;
        }
        if(resultInss > 1903.99 && resultInss <= 2826.65) {
            resultIrrf = (resultInss * IrrfFaixas.IRRF_FAIXA2) - IrrfFaixas.DEDUCAO_IRRF2;
        }
        if(resultInss > 2826.66 && resultInss <= 3751.05) {
            resultIrrf = (resultInss * IrrfFaixas.IRRF_FAIXA3) - IrrfFaixas.DEDUCAO_IRRF3;
        }
        if(resultInss > 3751.06 && resultInss <= 4664.68) {
            resultIrrf = (resultInss * IrrfFaixas.IRRF_FAIXA4) - IrrfFaixas.DEDUCAO_IRRF4;
        }
        if(resultInss > 4664.68) {
            resultIrrf = (resultInss * IrrfFaixas.IRRF_FAIXA5) - IrrfFaixas.DEDUCAO_IRRF5;
        }

        return resultIrrf.roundTwoDigits();
    }

    private fun calculoSalarioLiquido(salarioBruto: Double, inss: Double, irrf: Double, descontos: Double): Double {
        val resultSalarioLiquido = salarioBruto - inss - irrf - descontos
        return resultSalarioLiquido.roundTwoDigits();
    }

    private fun calculoPorcentagemDescontos(salarioBruto: Double, inss: Double, irrf: Double, descontos: Double): Double {
        val resultPorcentagem = (inss + irrf + descontos) * 100 / salarioBruto;
        return resultPorcentagem.roundTwoDigits();
    }
}
