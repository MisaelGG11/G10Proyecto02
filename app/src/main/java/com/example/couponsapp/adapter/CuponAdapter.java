package com.example.couponsapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.couponsapp.R;
import com.example.couponsapp.controladores.CuponControl;
import com.example.couponsapp.controladores.RestauranteControl;
import com.example.couponsapp.controladores.TipoCuponControl;
import com.example.couponsapp.controladores.UsuarioControl;
import com.example.couponsapp.modelos.Cupon;
import com.example.couponsapp.modelos.Restaurante;
import com.example.couponsapp.modelos.TipoCupon;
import com.example.couponsapp.modelos.Usuario;

import java.util.ArrayList;

public class CuponAdapter extends BaseAdapter {
    Context context;
    CuponControl control;
    RestauranteControl restauranteControl;
    TipoCuponControl tipoCuponControl;
    LayoutInflater layoutInflater;
    ArrayList<Cupon> items;
    ArrayList<Restaurante> restaurantes;
    ArrayList<TipoCupon> tipoCupons;
    //RestauranteAdapter restauranteAdapter;
    TipoCuponAdapter tipoCuponAdapter;

    //variables del custom dialog
    EditText restaurante,tipo, codigo, nombre, precio, disponible, horario, descripcion, nombreBusqueda;
    Spinner restauranteSpinner, tipoCuponSpinner;

    public CuponAdapter(Context context ) {
        this.context = context;
        this.control = new CuponControl(context);
        this.restauranteControl = new RestauranteControl(context);
        this.tipoCuponControl = new TipoCuponControl(context);
        this.items=control.all();
        this.restaurantes = restauranteControl.all();
        this.tipoCupons = tipoCuponControl.trarTipoCupones();
        this.layoutInflater=LayoutInflater.from(context);
        //restauranteAdapter = new RestauranteAdapter(context);
        tipoCuponAdapter = new TipoCuponAdapter(context);
    }

    public void crear() {
        Cupon targetItem=new Cupon();
        //creando el view del dialogo
        View customDialog=layoutInflater.inflate(R.layout.dialog_cupon, null);
        //restauranteSpinner =customDialog.findViewById(R.id.spinner_restaurante);
        //restauranteSpinner.setAdapter(restauranteAdapter);
        tipoCuponSpinner =customDialog.findViewById(R.id.spinner_tipo_cupon);
        tipoCuponSpinner.setAdapter(tipoCuponAdapter);

        //construccion del dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customDialog)
                .setTitle(R.string.dialog_crear)
                .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //AQUI DEBE RECIBIR EL ID_RESTAURANTE DEL USUARIO LOGEADO
                        Restaurante restauranteSelected = new Restaurante(1);
                        TipoCupon tipoCuponSelected = new TipoCupon();
                        int nuevoCupon = 0;
                        Dialog dialogView = (Dialog) dialog;
                        //nuevos valores
                        nombre=dialogView.findViewById(R.id.edittext_nombre_cupon);
                        codigo=dialogView.findViewById(R.id.edittext_codigo_cupon);
                        precio=dialogView.findViewById(R.id.edittext_precio_cupon);
                        horario=dialogView.findViewById(R.id.edittext_horario_cupon);
                        descripcion=dialogView.findViewById(R.id.edittext_descripcion_cupon);
                        //restaurante=dialogView.findViewById(R.id.spinner_restaurante);
                        tipoCuponSpinner=dialogView.findViewById(R.id.spinner_tipo_cupon);
                        //disponible=dialogView.findViewById(R.id.edittext_disponible_cupon);

                        //restauranteSelected=(Restaurante) restauranteSpinner.getSelectedItem();
                        tipoCuponSelected=(TipoCupon) tipoCuponSpinner.getSelectedItem();
                        nuevoCupon = tipoCuponSelected.getId_tipoCupon();

                        targetItem.setNombre_cupon(nombre.getText().toString());
                        targetItem.setCodigo_cupon(codigo.getText().toString());
                        targetItem.setPrecio(Double.valueOf(precio.getText().toString()));
                        targetItem.setHorario_cupon(horario.getText().toString());
                        targetItem.setDescripcion_cupon(descripcion.getText().toString());
                        targetItem.setRestaurante(restauranteSelected);
                        targetItem.setTipoCupon(tipoCuponSelected);
                        //targetItem.setDisponible(Integer.getInteger(disponible.getText().toString()));
                        int result=(int)control.insertCupon(targetItem);
                        targetItem.setId_cupon(result);
                        if (result>0){
                            addItem(targetItem);
                            notifyDataSetChanged();
                            Toast.makeText(context,R.string.guardado,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create();
        builder.show();
    }

    public  void editar(int position){
        //item a modificar
        Cupon targetItem=getItem(position);

        //creando el view del dialogo
        View customDialog=layoutInflater.inflate(R.layout.dialog_cupon, null);

        //valores por defecto
        nombre=customDialog.findViewById(R.id.edittext_nombre_cupon);
        codigo=customDialog.findViewById(R.id.edittext_codigo_cupon);
        precio=customDialog.findViewById(R.id.edittext_precio_cupon);
        horario=customDialog.findViewById(R.id.edittext_horario_cupon);
        descripcion=customDialog.findViewById(R.id.edittext_descripcion_cupon);

        tipoCuponSpinner=customDialog.findViewById(R.id.spinner_tipo_cupon);
        tipoCuponSpinner.setAdapter(tipoCuponAdapter);

        nombre.setText(targetItem.getNombre_cupon());
        codigo.setText(targetItem.getCodigo_cupon());
        precio.setText(targetItem.getPrecio().toString());
        horario.setText(targetItem.getHorario_cupon());
        descripcion.setText(targetItem.getDescripcion_cupon());
        for (int positionTarget=0;positionTarget<items.size();positionTarget++) {
            if(tipoCupons.get(positionTarget).getId_tipoCupon()==targetItem.getTipoCupon().getId_tipoCupon()){
                tipoCuponSpinner.setSelection(positionTarget);
            }
        }

        //construccion del dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customDialog)
                .setTitle(R.string.dialog_editar)
                .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //AQUI DEBE RECIBIR EL ID_RESTAURANTE DEL USUARIO LOGEADO
                        Restaurante restauranteSelected = new Restaurante(1);
                        TipoCupon tipoCuponSelected = new TipoCupon();
                        Dialog dialogView = (Dialog) dialog;
                        //nuevos valores
                        nombre=dialogView.findViewById(R.id.edittext_nombre_cupon);
                        codigo=dialogView.findViewById(R.id.edittext_codigo_cupon);
                        precio=dialogView.findViewById(R.id.edittext_precio_cupon);
                        horario=dialogView.findViewById(R.id.edittext_horario_cupon);
                        descripcion=dialogView.findViewById(R.id.edittext_descripcion_cupon);
                        tipoCuponSpinner=dialogView.findViewById(R.id.spinner_tipo_cupon);

                        tipoCuponSelected=(TipoCupon) tipoCuponSpinner.getSelectedItem();

                        targetItem.setNombre_cupon(nombre.getText().toString());
                        targetItem.setCodigo_cupon(codigo.getText().toString());
                        targetItem.setPrecio(Double.valueOf(precio.getText().toString()));
                        targetItem.setHorario_cupon(horario.getText().toString());
                        targetItem.setDescripcion_cupon(descripcion.getText().toString());
                        targetItem.setRestaurante(restauranteSelected);
                        targetItem.setTipoCupon(tipoCuponSelected);

                        int result=control.updateCupon(targetItem);
                        boolean isUpdated=result>0;
                        if (isUpdated){
                            items.set(position,targetItem);
                            notifyDataSetChanged();
                            Toast.makeText(context,R.string.guardado,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create();
        builder.show();

    }
    public void eliminar(int position){

        Cupon cupon=getItem(position);
        int result=control.deleteCupon(cupon.getId_cupon());
        boolean isDeleted=result>0;
        if (isDeleted){
            removeItem(position);
            notifyDataSetChanged();
            Toast.makeText(context, R.string.eliminado,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,R.string.no_eliminado,Toast.LENGTH_LONG).show();

        }
    }
    public void filtrar(String busqueda) {
        View customDialog=layoutInflater.inflate(R.layout.dialog_cupon, null);
        items=control.traerCupones(busqueda);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Cupon getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void removeItem(int i){
        items.remove(i);
    }
    public void addItem(Cupon item){
        items.add(item);
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {
        TextView tipoCupon,nombre_cupon,precio, codigo;
        TipoCupon tipoCuponSelected = new TipoCupon();

        //buscando el tipo cupon
        for (TipoCupon cuponTarget:tipoCupons) {
            if(cuponTarget.getId_tipoCupon()==items.get(position).getTipoCupon().getId_tipoCupon()){
                tipoCuponSelected=cuponTarget;
            }
        }

        if(customView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.item_cupon,parent,false);
        }else{
            customView=layoutInflater.inflate(R.layout.item_cupon,parent,false);
        }
        tipoCupon=customView.findViewById(R.id.tipo_cupon);
        nombre_cupon=customView.findViewById(R.id.nombre_cupon);
        codigo=customView.findViewById(R.id.codigo_cupon);
        precio=customView.findViewById(R.id.precio_cupo);
        tipoCupon.setText(String.valueOf(items.get(position).getTipoCupon().getNombre_tipo()));
        nombre_cupon.setText(items.get(position).getNombre_cupon());
        precio.setText(String.valueOf(items.get(position).getPrecio()));
        codigo.setText(String.valueOf(items.get(position).getCodigo_cupon()));
        //tipoCupon.setText(tipoCuponSelected.getNombre_tipo());
        return customView;
    }
}
