package com.mkr.lib.asynctask

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor

abstract class CoroutinesAsyncTask <Params,PROGRESS,Result> {

    open fun onPreExecute(){}
    abstract fun doInBackground(vararg voids: Void?): Result?
    open fun onPostExecute(result: Result?){}
    protected fun publishProgress(vararg progress :PROGRESS){
        GlobalScope.launch (Dispatchers.Main){
            onProgressUpdate(*progress)
        }
    }
    fun execute(vararg params: Params?){
      GlobalScope.launch (Dispatchers.Default){
          val result =doInBackground()
          withContext(Dispatchers.Main){
              onPostExecute(result)
          }
      }
    }
    fun executeOnExecutor(param: Executor, vararg params: Params?){
        GlobalScope.launch (Dispatchers.Default){
            val result =doInBackground()
            withContext(Dispatchers.Main){
                onPostExecute(result)
            }
        }
    }


    protected open fun onProgressUpdate(vararg values: PROGRESS){}
}