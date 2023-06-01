package com.example.couponsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.couponsapp.R;
import com.example.couponsapp.controladores.RestauranteControl;
import com.example.couponsapp.controladores.TipoCuponControl;
import com.example.couponsapp.modelos.Restaurante;
import com.example.couponsapp.modelos.TipoCupon;

import java.util.ArrayList;

public class TipoCuponAdapter extends BaseAdapter {
    Context context;
    TipoCuponControl control;
    LayoutInflater layoutInflater;
    ArrayList<TipoCupon> items;

    //variables del custom dialog
    EditText nombre,codigo;

    public TipoCuponAdapter(Context context ) {
        this.control = new TipoCuponControl(context);
        this.items=control.trarTipoCupones();
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void crear() {
        /*Municipio targetItem=new Municipio();
        //creando el view del dialogo
        View customDialog=layoutInflater.inflate(R.layout.dialog_especialidad, null);

        //construccion del dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customDialog)
                .setTitle(R.string.dialog_crear)
                .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String nuevoCodigo,nuevoNombre;
                        Dialog dialogView = (Dialog) dialog;
                        //nuevos valores
                        codigo=dialogView.findViewById(R.id.edittext_codigo_especialidad);
                        nombre=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        nuevoCodigo=codigo.getText().toString();
                        nuevoNombre=nombre.getText().toString();
                        targetItem.setCodigo_municipio(nuevoCodigo);
                        targetItem.setNombre_municipio(nuevoNombre);
                        int result=(int)control.insert(targetItem);
                        targetItem.setId_municipio(result);
                        if (result>0){
                            addItem(targetItem);
                            notifyDataSetChanged();
                            Toast.makeText(context,R.string.guardado,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create();
        builder.show();*/

    }

    public  void editar(int position){
        //item a modificar
        /*Municipio targetItem=getItem(position);

        //creando el view del dialogo
        View customDialog=layoutInflater.inflate(R.layout.dialog_especialidad, null);

        //valores por defecto
        codigo=customDialog.findViewById(R.id.edittext_codigo_especialidad);
        nombre=customDialog.findViewById(R.id.edittext_nombre_especialidad);
        codigo.setText(targetItem.getCodigo_municipio());
        nombre.setText(targetItem.getNombre_municipio());

        //construccion del dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customDialog)
                .setTitle(R.string.dialog_editar)
                .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String nuevoCodigo,nuevoNombre;
                        Dialog dialogView = (Dialog) dialog;
                        //nuevos valores
                        codigo=dialogView.findViewById(R.id.edittext_codigo_especialidad);
                        nombre=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        nuevoCodigo=codigo.getText().toString();
                        nuevoNombre=nombre.getText().toString();
                        if (targetItem.getCodigo_municipio()!=nuevoCodigo){
                            targetItem.setCodigo_municipio(nuevoCodigo);
                        }
                        if(targetItem.getNombre_municipio()!=nuevoNombre){
                            targetItem.setNombre_municipio(nuevoNombre);
                        }

                        int result=control.update(targetItem);
                        boolean isUpdated=result>0;
                        if (isUpdated){
                            replaceItem(position,targetItem);
                            notifyDataSetChanged();
                            Toast.makeText(context,R.string.guardado,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create();
        builder.show();*/

    }
    public void eliminar(int position){

       /* Municipio Municipio=getItem(position);
        int result=control.delete(Municipio.getId_municipio());
        boolean isDeleted=result>0;
        if (isDeleted){
            removeItem(position);
            notifyDataSetChanged();
            Toast.makeText(context,R.string.eliminado,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,R.string.no_eliminado,Toast.LENGTH_LONG).show();

        }*/
    }
    public void filtrar(String busqueda) {
        /*items= control.listaMunicipios(busqueda);
        notifyDataSetChanged();*/

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TipoCupon getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void removeItem(int i){
        items.remove(i);
    }
    public void addItem(TipoCupon item){
        items.add(item);
    }
    public void replaceItem(int position,TipoCupon item){
        items.set(position,item);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {
        TextView nombre,codigo;
        if(customView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.item_restaurante,parent,false);
        }else{
            customView=layoutInflater.inflate(R.layout.item_restaurante,parent,false);
        }
        nombre=customView.findViewById(R.id.nombre);
        codigo=customView.findViewById(R.id.codigo);
        nombre.setText(items.get(position).getNombre_tipo());
        codigo.setText(String.valueOf(items.get(position).getId_tipoCupon()));
        return customView;
    }
}
