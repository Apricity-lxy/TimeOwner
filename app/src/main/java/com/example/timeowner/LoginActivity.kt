package com.example.timeowner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var counter = 3


        val insetsController = WindowCompat.getInsetsController(
            window, window.decorView
        )
        if (!isDarkTheme(this)){

            //insetsController?.isAppearanceLightStatusBars = true
            //insetsController?.isAppearanceLightNavigationBars = true
            insetsController?.apply {
                isAppearanceLightStatusBars = true


            }

        }
        insetsController?.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        insetsController?.hide(WindowInsetsCompat.Type.navigationBars())
        window.statusBarColor = Color.TRANSPARENT
        val dbhelper = MyDatabaseHelper(this,"accountdata.db",1)
        create_text.setOnClickListener {
            val intent = Intent(this,signup_Activity::class.java)
            startActivity(intent)
        }
        forgot.setOnClickListener {
            val account = signin_accountEdit.text.toString()

            val intent = Intent(this,ForgotActivity::class.java)
            intent.putExtra("forgot_account",account)
            startActivity(intent)
            finish()
        }
        show_check.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                signin_passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            }else{
                signin_passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance())
            }
        }
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isremember = prefs.getBoolean("remember_password",false)
        if (isremember) {
            val account_remember = prefs.getString("account","")
            val password_remember = prefs.getString("password","")
            signin_accountEdit.setText(account_remember)
            signin_passwordEdit.setText(password_remember)
            remember_check.isChecked = true
        }
        signin_Button.setOnClickListener {
            counter--
            var flag1 = true
            val account = signin_accountEdit.text.toString()
            val password = signin_passwordEdit.text.toString()
            val db = dbhelper.writableDatabase
            val cursor = db.query("accountdata",null,null,null,null,null,null)
            val editor = prefs.edit()


            if (cursor.moveToFirst()) {
                do {
                    val account_exist = cursor.getString(cursor.getColumnIndexOrThrow("account"))
                    val password_exist = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                    val email_exist = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                    if (account_exist==account && password_exist==password && account!= "" && password != ""){
                        flag1 = false
                        if (remember_check.isChecked) {
                            editor.putBoolean("remember_password",true)
                            editor.putString("account",account)
                            editor.putString("password",password)
                        }else{
                            editor.clear()
                        }
                        editor.apply()
                        val intent = Intent(this,MainActivity::class.java)
                        intent.putExtra("user_name",account)
                        intent.putExtra("user_email",email_exist)
                        startActivity(intent)
                        finish()
                    }

                }while (cursor.moveToNext())
            }
            cursor.close()
            if (flag1) {
                Toast.makeText(this, "剩余可尝试次数为 $counter 次", Toast.LENGTH_SHORT).show()
            }
            if (counter==0) {
                AlertDialog.Builder(this).apply {
                    setTitle("错误：")
                    setMessage("账号或密码错误，请检查或者重新注册")
                    setCancelable(false)
                    setPositiveButton("OK") { _, _ ->
                        ActivityCollector.finishAll()
                        val i = Intent(context, LoginActivity::class.java)
                        context.startActivity(i)
                        finish()

                    }

                    show()
                }
            }
        }
    }
    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }

}