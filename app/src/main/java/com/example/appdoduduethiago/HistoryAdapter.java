package com.example.appdoduduethiago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<Tarefa> tarefaLista;
    private Context contexto;

    public HistoryAdapter(ArrayList<Tarefa> taskList, Context context) {
        this.tarefaLista = taskList;
        this.contexto = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tarefa task = tarefaLista.get(position);
        holder.tituloTextView.setText(task.getTitulo());
        holder.dataTextView.setText(task.getData());
        holder.descricaoTextView.setText(task.getDescricao());
        holder.descricaoTextView.setVisibility(View.GONE);
        holder.checkBox.setChecked(task.isCompleto());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompletado(isChecked);
            ((MainActivity) contexto).saveTasks();
        });
        holder.tituloTextView.setOnClickListener(v -> {
            if (holder.descricaoTextView.getVisibility() == View.VISIBLE) {
                holder.descricaoTextView.setVisibility(View.GONE);
            } else {
                holder.descricaoTextView.setVisibility(View.VISIBLE);
            }
        });
        holder.deletaButton.setOnClickListener(v -> {
            tarefaLista.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tarefaLista.size());

            if (contexto instanceof MainActivity) {
                ((MainActivity) contexto).saveTasks();
            }
            Toast.makeText(contexto, "Task deleted", Toast.LENGTH_SHORT).show();
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tituloTextView;
        public TextView dataTextView;
        public TextView descricaoTextView;
        public Button deletaButton;
        public CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.taskTitle);
            dataTextView = itemView.findViewById(R.id.taskDate);
            descricaoTextView = itemView.findViewById(R.id.taskDescription);
            deletaButton = itemView.findViewById(R.id.deleteButton);
            checkBox = itemView.findViewById(R.id.checkBoxCompleted);
        }
    }

    @Override
    public int getItemCount() {
        return tarefaLista.size();
    }
}
