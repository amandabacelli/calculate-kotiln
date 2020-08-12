package br.com.abacelli.calculatorigti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val salarioBruto = intent.getDoubleExtra("SALARIOBRUTO", 0.0);
        val inss = intent.getDoubleExtra("INSS", 0.0);
        val irrf = intent.getDoubleExtra("IRRF", 0.0);
        val descontos = intent.getDoubleExtra("DESCONTOS", 0.0);
        val salarioLiquido = intent.getDoubleExtra("SALARIOLIQUIDO", 0.0);
        val porcentagemDescontos = intent.getDoubleExtra("PORCENTAGEMDESCONTOS", 0.0);

        txtResultSalario.text = salarioBruto.toString();
        txtNumINSS.text = inss.toString();
        txtNumIRRF.text = irrf.toString();
        txtNumDescontResult.text = descontos.toString();
        txtSalarioLiquidoResult2.text = salarioLiquido.toString();
        txtDescontoResult.text = porcentagemDescontos.toString();

        btnVoltar.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }
}