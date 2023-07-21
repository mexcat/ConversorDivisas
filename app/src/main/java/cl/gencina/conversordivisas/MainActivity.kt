package cl.gencina.conversordivisas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import cl.gencina.conversordivisas.databinding.ActivityMainBinding
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listaDivisa = ArrayList<Divisa>()
    lateinit var desdeDivisa:Divisa
    lateinit var aConvertirDivisa:Divisa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarSpinner()
    }

    private fun cargarSpinner() {
        listaDivisa.add(Divisa("Dolar","US$","USD" ))
        listaDivisa.add(Divisa("Euro", "€","EUR" ))
        listaDivisa.add(Divisa("Peso Chileno", "$","CLP" ))

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listaDivisa)
        binding.spDesdeDivisa.adapter = adapter
        binding.spHaciaDivisa.adapter = adapter

        initListener()
    }

    private fun initListener() {
        binding.spDesdeDivisa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                desdeDivisa = listaDivisa[0]
                Log.e("desdeInit", desdeDivisa.toString())
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                desdeDivisa = binding.spDesdeDivisa.selectedItem as Divisa
                Log.e("desde", desdeDivisa.toString())
            }
        }

        binding.spHaciaDivisa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                aConvertirDivisa = listaDivisa[0]
                Log.e("haciaInit", aConvertirDivisa.toString())
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                aConvertirDivisa = binding.spHaciaDivisa.selectedItem as Divisa
                Log.e("hacia", aConvertirDivisa.toString())
            }
        }

        binding.btnLimpiar.setOnClickListener { limpiar() }
        binding.btnConvertir.setOnClickListener { calcular() }
    }



    private fun calcular() {
        var valor = binding.teConvertValue.text?:"0.0"
        var resultado = 0.0

        if(!valor.isNullOrBlank()){
                when (desdeDivisa.nombreCorto) {
                    "USD" -> {resultado =
                        when(aConvertirDivisa.nombreCorto){
                            "CLP"-> valor.toString().toDouble() * 817.0
                            "USD"-> valor.toString().toDouble()
                            "EUR" -> valor.toString().toDouble()* 0.89
                            else->0.0
                        }
                    }
                    "CLP"-> {resultado =
                        when(aConvertirDivisa.nombreCorto){
                            "CLP"-> valor.toString().toDouble()
                            "USD"-> valor.toString().toDouble() * 0.001
                            "EUR" -> valor.toString().toDouble()* 0.001
                            else->0.0
                        }
                    }
                    "EUR"-> {resultado =
                        when(aConvertirDivisa.nombreCorto){
                            "CLP"-> valor.toString().toDouble()* 910
                            "USD"-> valor.toString().toDouble() * 1.11
                            "EUR" -> valor.toString().toDouble()
                            else->0.0
                        }
                    }
            }
            mostrar(resultado)
        }else{
            binding.teConvertValue.setText("0.0")
        }
    }

    private fun mostrar(resultado: Double) {
        "${aConvertirDivisa.icono} $resultado".also { binding.tvResultado.text = it }
    }

    private fun limpiar() {
        binding.teConvertValue.setText("0")
        binding.tvResultado.text = "0"
    }

}