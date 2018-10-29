package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.MedidorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PontoEntregaController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.DemandaClassficacao;
import com.visium.fieldservice.entity.DemandaComplemento1;
import com.visium.fieldservice.entity.DemandaComplemento2;
import com.visium.fieldservice.entity.DemandaTipoImovel;
import com.visium.fieldservice.entity.Fase;
import com.visium.fieldservice.entity.Lagradouro;
import com.visium.fieldservice.entity.NumeroLocal;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaClasseSocial;
import com.visium.fieldservice.entity.PontoEntregaLocation;
import com.visium.fieldservice.entity.PontoEntregaPhotos;
import com.visium.fieldservice.entity.PontoEntregaTipodeConstrução;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.entity.PostType;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.entity.TipoDemanda;
import com.visium.fieldservice.entity.VaosPontoPoste;
import com.visium.fieldservice.ui.dialog.adapter.AlturaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.DemandaClassificacaoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.DemandaComplemento1Adapter;
import com.visium.fieldservice.ui.dialog.adapter.DemandaComplemento2Adapter;
import com.visium.fieldservice.ui.dialog.adapter.DemandaTipoImovelAdapter;
import com.visium.fieldservice.ui.dialog.adapter.EsforcoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.FaseAdapter;
import com.visium.fieldservice.ui.dialog.adapter.NumeroLocalAdapter;
import com.visium.fieldservice.ui.dialog.adapter.OcupanteDAdapter;
import com.visium.fieldservice.ui.dialog.adapter.OcupanteSAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaClasseSocialAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaTipoDemandaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaTipoConstrucaoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.TipoAdapter;
import com.visium.fieldservice.ui.maps.MapsPickLocationActivity;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.ui.maps.MapsPostsAssociaPontoActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */

public class PostPontoEditDialogFragment extends AppCompatDialogFragment {

    private static final int PICKUP_LOCATION = 1;

    private static PontoEntrega mPontoEntrega;
    private static VaosPontoPoste mVaosPontoPoste;
    private static PontoEntregaEditDialogListener mListener;
    private static Button mImageAdd, mSetStart, btn_mudar_posicao;
    private static LatLng mUserLatLng;
    private static boolean mCreating;
    private LinearLayout mLayoutImages;
    private ScrollView mScrollView;
    private static Spinner mType, mEffort, mHeight, ocupante_s, ocupante_d;
    private static EditText mObservations;
    private static List<PontoEntregaPhotos> photosList;
    private static Activity activity;
    private AlertDialog alertDialog;
    private GridView gridAltura, grid_esforco, grid_post_tipo, grid_ocupante_s, grid_ocupante_d, gridNumeroLocal, gridNumeroAndarLocal, gridNumeroTotalApartamentos;
    private AlturaAdapter alturaAdapter;
    private EsforcoAdapter esforcoAdapter;
    private TipoAdapter tipoAdapter;
    private OcupanteSAdapter ocupanteSAdapter;
    private OcupanteDAdapter ocupanteDAdapter;
    private static TextView post_seq_edit, post_seq_effort, post_seq_tipo, post_seq_ocupantes, post_seq_ocupanted;
    private String esforco, tipo_visible;
    private double altura;
    private int ocupantes, ocupanted, tipo;
    private Button associa_poste;
    private TextView altura_text, tipo_text, obs_text;
    private LatLng latLng;
    private static Post mPost;
    private VaosPontoPoste vaosDeletar;
    private boolean trocaPositionVaoPOnto = false;
    private boolean podePassar = false;

    private ViewFlipper viewFlipper;
    private static TextView post_seq_tipoDemanda, post_seq_classficacao, post_seq_complemento1, post_seq_tipo_imovel, post_seq_complemento2,
            post_seq_nunero_local, post_seq_numero_total_apartamentos, post_seq_numero_andares_edificio, post_seq_nome_edificio;

    private static String seq_lagradouro, seq_numero, seq_fase, seq_etligacao;
    private static int seq_tipo_demanda;
    private String seq_classificacao, set_tipo_complemento1, set_tipo_complemento2, seq_tipo_imovel, seq_numero_local, seq_numero_andares_edificio,
            seq_numeto_total_apartamentos, seq_nome_edifio;
    private LinearLayout layoutImages_ponto_entrega;
    private EditText mLogradouro, mNumero, mMedidor, mFase, mETLigacao, mObservacao, edt_nome_edificio, edt_numero_local,
            edt_numero_total_apartamentos, edt_numero_andar_edificio;
    private GridView gridTipoDemanda, grid_classificacao, grid_complemento1, grid_tipo_imovel, grid_complemento2;
    private PontoEntregaTipoDemandaAdapter pontoEntregaTipoDemandaAdapter;
    private DemandaClassificacaoAdapter demandaClassificacaoAdapter;
    private DemandaComplemento1Adapter demandaComplemento1Adapter;
    private DemandaComplemento2Adapter demandaComplemento2Adapter;
    private DemandaTipoImovelAdapter demandaTipoImovelAdapter;
    private NumeroLocalAdapter numeroLocalAdapter;
    private NumeroLocalAdapter numeroAndarLocalAdapter, numeroTotalApartamentosAdapter;
    private static List<PontoEntregaPhotos> pontoEntregaPhotosList;
    private static Lagradouro lagradouro;
    private boolean deletarPlyline;
    private EditText edt_complemento1, edt_complemento2;
    private LinearLayout linear_numero_andar_edificio;
    String numeroLocal = "";
    String numeroAndar = "";
    String numeroAparatamentos = "";

    private List<LatLng> mOrderPoints;
    private ServiceOrderDetails mOrderDetails;

    public static AppCompatDialogFragment newInstance(Activity activity, PontoEntregaEditDialogListener listener, PontoEntrega post) {
        return PostPontoEditDialogFragment.newInstance(activity, listener, post, null);
    }

    public static AppCompatDialogFragment newInstance(Activity activity, PontoEntregaEditDialogListener listener, PontoEntrega pontoEntrega, LatLng mUserLatLng) {
        PostPontoEditDialogFragment.mPontoEntrega = pontoEntrega;
        PostPontoEditDialogFragment.mListener = listener;
        PostPontoEditDialogFragment.mCreating = mUserLatLng != null;
        PostPontoEditDialogFragment.activity = activity;
        PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        if (PostPontoEditDialogFragment.mCreating) {
            PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        }
        return new PostPontoEditDialogFragment();
    }

    public static AppCompatDialogFragment newInstance(Activity activity, PontoEntregaEditDialogListener listener, PontoEntrega pontoEntrega, LatLng mUserLatLng, Lagradouro lagradouro, Post post) {
        PostPontoEditDialogFragment.mPontoEntrega = pontoEntrega;
        PostPontoEditDialogFragment.mListener = listener;
        PostPontoEditDialogFragment.mCreating = mUserLatLng != null;
        PostPontoEditDialogFragment.activity = activity;
        PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        PostPontoEditDialogFragment.lagradouro = lagradouro;
        PostPontoEditDialogFragment.mPost = post;
        if (PostPontoEditDialogFragment.mCreating) {
            PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        }
        return new PostPontoEditDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public void addImage(Context c, final String value, boolean isNew) {
        final LinearLayout l = new LinearLayout(c);
        final EditText lEdit = new EditText(c);
        //ViewUtils.setViewMargins(c, new LinearLayout.LayoutParams(), 0, 0, 30, 10, lEdit);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 60, 20);
        lEdit.setLayoutParams(layoutParams);
        lEdit.setText(value);
        Button b = new Button(c);
        b.setText("Apagar");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutImages_ponto_entrega.removeView(l);
                int toRemove = -1;
                for (int i = 0; i < photosList.size(); i++) {
                    PontoEntregaPhotos p = photosList.get(i);
                    if (p.getNumber().equals(value)) {
                        toRemove = i;
                        break;
                    }
                }
                photosList.remove(toRemove);
            }
        });
        l.addView(lEdit);
        l.addView(b);
        layoutImages_ponto_entrega.addView(l);
        if (isNew) {
            Calendar cc = Calendar.getInstance();
            //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(cc.getTime());
            LogUtils.log("Date = " + date);
            photosList.add(new PontoEntregaPhotos(value, date));
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //   post = new Post();
        // criaForm();
        photosList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_pontoentrega_edit, container, false);
        mSetStart = (Button) view.findViewById(R.id.button_set_start);
        mImageAdd = (Button) view.findViewById(R.id.button_add);
        layoutImages_ponto_entrega = (LinearLayout) view.findViewById(R.id.layoutImages_ponto_entrega);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll_ponto_entrega);
        mLayoutImages = (LinearLayout) view.findViewById(R.id.layoutImages);
        post_seq_tipoDemanda = (TextView) view.findViewById(R.id.post_seq_tipoDemanda);
        post_seq_classficacao = (TextView) view.findViewById(R.id.post_seq_classficacao);
        post_seq_complemento1 = (TextView) view.findViewById(R.id.post_seq_complemento1);
        post_seq_complemento2 = (TextView) view.findViewById(R.id.post_seq_complemento2);
        post_seq_tipo_imovel = (TextView) view.findViewById(R.id.post_seq_tipo_imovel);
        //associa_poste = (Button) view.findViewById(R.id.associa_poste);
        edt_complemento1 = (EditText) view.findViewById(R.id.edt_complemento1);
        edt_complemento2 = (EditText) view.findViewById(R.id.edt_complemento2);
        //altura_text = (TextView) view.findViewById(R.id.altura_text);
        //tipo_text = (TextView) view.findViewById(R.id.tipo_text);
        //obs_text = (TextView) view.findViewById(R.id.obs_text);
        final Button back_button_ponto_entrega, next_button_ponto_entrega;
        back_button_ponto_entrega = (Button) view.findViewById(R.id.back_button_ponto_entrega);
        next_button_ponto_entrega = (Button) view.findViewById(R.id.next_button_ponto_entrega);
        gridTipoDemanda = (GridView) view.findViewById(R.id.gridTipoDemanda);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.vs_ponto_entrega);
        grid_classificacao = (GridView) view.findViewById(R.id.grid_classificacao);
        grid_tipo_imovel = (GridView) view.findViewById(R.id.grid_tipo_imovel);
        grid_complemento1 = (GridView) view.findViewById(R.id.grid_complemento1);
        grid_complemento2 = (GridView) view.findViewById(R.id.grid_complemento2);
        post_seq_nunero_local = (TextView) view.findViewById(R.id.post_seq_nunero_local);
        post_seq_numero_total_apartamentos = (TextView) view.findViewById(R.id.post_seq_numero_total_apartamentos);
        post_seq_numero_andares_edificio = (TextView) view.findViewById(R.id.post_seq_numero_andares_edificio);
        post_seq_nome_edificio = (TextView) view.findViewById(R.id.post_seq_nome_edificio);
        edt_nome_edificio = (EditText) view.findViewById(R.id.edt_nome_edificio);
        edt_numero_andar_edificio = (EditText) view.findViewById(R.id.edt_numero_andar_edificio);
        edt_numero_total_apartamentos = (EditText) view.findViewById(R.id.edt_numero_total_apartamentos);
        edt_numero_local = (EditText) view.findViewById(R.id.edt_numero_local);
       // btn_mudar_posicao = (Button) view.findViewById(R.id.btn_mudar_posicao);
        linear_numero_andar_edificio = (LinearLayout) view.findViewById(R.id.linear_numero_andar_edificio);
        gridNumeroLocal = (GridView) view.findViewById(R.id.gridNumeroLocal);
        gridNumeroAndarLocal = (GridView) view.findViewById(R.id.gridNumeroAndarLocal);
        gridNumeroTotalApartamentos = (GridView) view.findViewById(R.id.gridNumeroTotalApartamentos);

        final Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);

        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);

        linear_numero_andar_edificio.setVisibility(LinearLayout.GONE);

        final int fim = 8;
        final int[] count = {0};

        seq_numero_andares_edificio="";
        seq_numeto_total_apartamentos = "";
        seq_nome_edifio = "";

        //  builder.setView(view);

        //List<PostHeightGrid> postHeightGrids = Arrays.asList(PostHeight.values());

        final List<TipoDemanda> tipoDemandas = Arrays.asList(TipoDemanda.values());
        final List<DemandaClassficacao> demandaClassficacaos = Arrays.asList(DemandaClassficacao.values());
        final List<DemandaComplemento1> demandaComplemento1s = Arrays.asList(DemandaComplemento1.values());
        final List<DemandaComplemento2> demandaComplemento2s = Arrays.asList(DemandaComplemento2.values());
        final List<DemandaTipoImovel> demandaTipoImovels = Arrays.asList(DemandaTipoImovel.values());
        final List<NumeroLocal> numeroLocals = Arrays.asList(NumeroLocal.values());
        final List<NumeroLocal> numeroAndarLocal = Arrays.asList(NumeroLocal.values());
        final List<NumeroLocal> numeroTotalApartamentos = Arrays.asList(NumeroLocal.values());

        /*alturaAdapter = new AlturaAdapter(getActivity(), list);
        gridAltura.setAdapter(alturaAdapter);

        esforcoAdapter = new EsforcoAdapter(getActivity(), list_effort);
        grid_esforco.setAdapter(esforcoAdapter);

        tipoAdapter = new TipoAdapter(getActivity(), list_type);
        grid_post_tipo.setAdapter(tipoAdapter);

        ocupanteSAdapter = new OcupanteSAdapter(getActivity(), list_Ocupantes);
        grid_ocupante_s.setAdapter(ocupanteSAdapter);

        ocupanteDAdapter = new OcupanteDAdapter(getActivity(), list_OcupanteD);
        grid_ocupante_d.setAdapter(ocupanteDAdapter);*/

        pontoEntregaTipoDemandaAdapter = new PontoEntregaTipoDemandaAdapter(getContext(), tipoDemandas);
        gridTipoDemanda.setAdapter(pontoEntregaTipoDemandaAdapter);

        demandaClassificacaoAdapter = new DemandaClassificacaoAdapter(getContext(), demandaClassficacaos);
        grid_classificacao.setAdapter(demandaClassificacaoAdapter);


        demandaComplemento1Adapter = new DemandaComplemento1Adapter(getContext(), demandaComplemento1s);
        grid_complemento1.setAdapter(demandaComplemento1Adapter);

        demandaComplemento2Adapter = new DemandaComplemento2Adapter(getContext(), demandaComplemento2s);
        grid_complemento2.setAdapter(demandaComplemento2Adapter);

        demandaTipoImovelAdapter = new DemandaTipoImovelAdapter(getContext(), demandaTipoImovels);
        grid_tipo_imovel.setAdapter(demandaTipoImovelAdapter);

        numeroLocalAdapter  = new NumeroLocalAdapter(getContext(), numeroLocals);
        gridNumeroLocal.setAdapter(numeroLocalAdapter);

        numeroAndarLocalAdapter = new NumeroLocalAdapter(getContext(), numeroAndarLocal);
        gridNumeroAndarLocal.setAdapter(numeroAndarLocalAdapter);

        numeroTotalApartamentosAdapter = new NumeroLocalAdapter(getContext(), numeroTotalApartamentos);
        gridNumeroTotalApartamentos.setAdapter(numeroTotalApartamentosAdapter);



        seq_fase = "";

        gridTipoDemanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                seq_tipo_demanda = i;
                if (tipoDemandas.get(i).getTipo().equals("SEM INFORMACAO") || tipoDemandas.get(i).getTipo().equals("SEM_INFORMACAO")) {
                    post_seq_tipoDemanda.setText("...");
                } else {
                    post_seq_tipoDemanda.setText(tipoDemandas.get(i).getTipo());
                }
                viewFlipper.showNext();
                ++count[0];
                if(seq_tipo_demanda == 1){
                    podePassar = false;
                    post_seq_numero_total_apartamentos.setVisibility(View.VISIBLE);
                    post_seq_numero_andares_edificio.setVisibility(View.VISIBLE);
                    post_seq_nome_edificio.setVisibility(View.VISIBLE);
                }else{
                    podePassar = true;
                    post_seq_numero_andares_edificio.setText("");
                    post_seq_numero_total_apartamentos.setText("");
                    post_seq_nome_edificio.setText("");
                    seq_nome_edifio = "";
                    seq_numero_andares_edificio = "";
                    seq_numeto_total_apartamentos = "";
                }
            }
        });
        grid_classificacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DemandaClassficacao demandaClassficacao = demandaClassficacaos.get(i);
                seq_classificacao = demandaClassficacao.getClassificacao();
                if (demandaClassficacaos.get(i).getClassificacao().equals("SEM INFORMACAO") || demandaClassficacaos.get(i).getClassificacao().equals("SEM_INFORMACAO")) {
                    post_seq_classficacao.setText("/...");
                } else {
                    post_seq_classficacao.setText("/" + demandaClassficacaos.get(i).getClassificacao());
                }
                viewFlipper.showNext();
                ++count[0];
            }
        });
        grid_tipo_imovel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DemandaTipoImovel demandaTipoImovel = demandaTipoImovels.get(i);

                seq_tipo_imovel = demandaTipoImovel.getTipo();
                post_seq_tipo_imovel.setText("/" + demandaTipoImovels.get(i).getTipo());
                viewFlipper.showNext();
                ++count[0];
            }
        });
        grid_complemento1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edt_complemento1.setText("");
                DemandaComplemento1 demandaComplemento1 = demandaComplemento1s.get(i);

                set_tipo_complemento1 = demandaComplemento1.getComplemento();
                if (demandaComplemento1s.get(i).getComplemento().equals("SEM INFORMACAO") || demandaComplemento1s.get(i).getComplemento().equals("SEM_INFORMACAO")) {
                    post_seq_complemento1.setText("/...");
                } else {
                    post_seq_complemento1.setText("/" + demandaComplemento1s.get(i).getComplemento());
                }
                viewFlipper.showNext();
                ++count[0];
            }
        });

        grid_complemento2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edt_complemento2.setText("");
                DemandaComplemento2 demandaComplemento2 = demandaComplemento2s.get(i);
                set_tipo_complemento2 = demandaComplemento2.getComplemento();
                post_seq_complemento2.setText("/" + demandaComplemento2s.get(i).getComplemento());
                viewFlipper.showNext();
                ++count[0];
            }
        });

        gridNumeroLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 10){
                    numeroLocal = "";
                }else{
                    numeroLocal += numeroLocals.get(position).getNumero().toString();
                }
                edt_numero_local.setText(numeroLocal);
            }
        });

        gridNumeroAndarLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 10){
                    numeroAndar = "";
                }else{
                    numeroAndar += numeroAndarLocal.get(position).getNumero().toString();
                }
                edt_numero_andar_edificio.setText(numeroAndar);
            }
        });

        gridNumeroTotalApartamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 10){
                    numeroAparatamentos = "";
                }else{
                    numeroAparatamentos += numeroTotalApartamentos.get(position).getNumero().toString();
                }
                edt_numero_total_apartamentos.setText(numeroAparatamentos);
            }
        });

      /*  final List<Fase> faseList = Arrays.asList(Fase.values());
        FaseAdapter faseAdapter = new FaseAdapter(getContext(), faseList);
        gridFase.setAdapter(faseAdapter);
        gridFase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(faseList.get(i).getName().equals("SEM INFORMACAO")){
                    post_seq_fase.setText("/...");
                    seq_fase = "SEM INFORMACAO";
                }else{
                    seq_fase =  faseList.get(i).getName().toString();
                    post_seq_fase.setText(seq_fase);
                }

                ++count[0];
                viewFlipper.showNext();
            }
        });*/

        final PostPontoEditDialogFragment c = this;

        mSetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verifier.prompt(c.getContext());
            }
        });

        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Verifier.getPontoNextNumber(mPontoEntrega.getGeoCode(), c);
                if (n > 0)
                    addImage(c.getContext(), String.format("%04d", n), true);
            }
        });

        if (mPontoEntrega.getPhotos() != null) {
            List<PontoEntregaPhotos> imagesList = mPontoEntrega.getPhotos();
            for (PontoEntregaPhotos pp : imagesList) {
                addImage(c.getContext(), pp.getNumber(), false);
                photosList.add(pp);
            }
        }


//        btn_mudar_posicao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), MapsPickLocationActivity.class);
//                // intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(mListener.getPostsList()));
//                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mListener.getOrderPoints()));
//
//                intent.putExtra("orderId", mPontoEntrega.getOrderId());
//
//                if (PostPontoEditDialogFragment.mCreating) {
//                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, PostPontoEditDialogFragment.mUserLatLng);
//                    //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
//                } else {
//                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, latLng);
//                    trocaPositionVaoPOnto = true;
//                }
//                startActivityForResult(intent, PICKUP_LOCATION);
//
//            }
//
//        });

      /*  view.findViewById(R.id.location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsPickLocationActivity.class);
               // intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(mListener.getPostsList()));
                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mListener.getOrderPoints()));

                intent.putExtra("orderId", mPontoEntrega.getOrderId());

                if (PostPontoEditDialogFragment.mCreating) {
                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, PostPontoEditDialogFragment.mUserLatLng);
                    //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
                } else {
                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, mPontoEntrega.getLocation().toLatLng());
                }
                startActivityForResult(intent, PICKUP_LOCATION);
            }
        });*/


        //mTransaction = (SwitchCompat) view.findViewById(R.id.transaction_switch);
        if (mCreating) {
            //mTransaction.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.title))
                    .setText(R.string.dialog_ponto_entrega_edit_creating_title);
            // mType.setSelection(PostType.SEM_INFORMACAO.ordinal());
            //mTransaction.setChecked(mPost.isClosed());
            //  mObservations.setText("Teste de Obs");
            // mHeight.setSelection(0);
            // mEffort.setSelection(0);
            // mType.setSelection(0);
            //  ocupante_s.setSelection(0);
            //   ocupante_d.setSelection(0);
            Location l = mListener.getLastLocation();
            //mPontoEntrega.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
            mPontoEntrega.setX_atualizacao(l.getLatitude());
            mPontoEntrega.setY_atualizacao(l.getLongitude());
            mPontoEntrega.setUpdate(true);
        } else {
            ((TextView) view.findViewById(R.id.title))
                    .setText(getString(R.string.dialog_post_edit_title, mPontoEntrega.getGeoCode()));
            //mTransaction.setChecked(mPost.isClosed());
            // Editar
            /*  mObservations.setText(mPost.getObservations());
            altura = PostHeight.parse(mPost.getHeight()).getHeight();
            esforco = PostEffort.parse(mPost.getPostEffort()).getName();
            ocupantes = OcupantesS.parse(mPost.getOcupante_s()).ordinal();
            ocupanted = OcupantesD.parse(mPost.getOcupante_d()).ordinal();
            tipo =  mPost.getPostType();*/

            seq_tipo_demanda = mPontoEntrega.getTipoDemanda();
            seq_classificacao = mPontoEntrega.getClassificacao();
            seq_tipo_imovel = mPontoEntrega.getTipoImovel();
            set_tipo_complemento1 = mPontoEntrega.getComplemento1();
            set_tipo_complemento2 = mPontoEntrega.getComplemento2();

            List<CharSequence> tipoDemanda = TipoDemanda.getNames();
            CharSequence tipoDemandaEdit = tipoDemanda.get(mPontoEntrega.getTipoDemanda());

            post_seq_tipoDemanda.setText(tipoDemandaEdit.toString());
            post_seq_classficacao.setText("/" + mPontoEntrega.getClassificacao());
            post_seq_tipo_imovel.setText("/" + mPontoEntrega.getTipoImovel());
            post_seq_complemento1.setText("/" + mPontoEntrega.getComplemento1());
            post_seq_complemento2.setText("/" + mPontoEntrega.getComplemento2());

            post_seq_nunero_local.setText("/"+mPontoEntrega.getNumero_local());
            post_seq_numero_andares_edificio.setText("/"+mPontoEntrega.getNumero_andares_edificio());
            post_seq_numero_total_apartamentos.setText("/"+mPontoEntrega.getNumero_total_apartamentos());
            post_seq_nome_edificio.setText("/"+mPontoEntrega.getNome_edificio());
            /*edt_complemento1.setText(mPontoEntrega.getComplemento1());
            edt_complemento2.setText(mPontoEntrega.getComplemento2());*/

            edt_numero_local.setText(mPontoEntrega.getNumero_local()+"");
            edt_numero_andar_edificio.setText(mPontoEntrega.getNumero_andares_edificio()+"");
            edt_numero_total_apartamentos.setText(mPontoEntrega.getNumero_total_apartamentos()+"");
            edt_nome_edificio.setText(mPontoEntrega.getNome_edificio()+"");

            if(mPontoEntrega.getNumero_total_apartamentos() == 0){
                post_seq_numero_total_apartamentos.setVisibility(View.GONE);
            }
            if(mPontoEntrega.getNumero_andares_edificio() == 0){
                post_seq_numero_andares_edificio.setVisibility(View.GONE);
            }
            if(mPontoEntrega.getNome_edificio().equals("")){
                post_seq_nome_edificio.setVisibility(View.GONE);
            }



           /* PostType type = list_type.get(tipo);

            post_seq_edit.setText(altura+"");
            post_seq_effort.setText(" / "+esforco);
            post_seq_tipo.setText(" / "+type.getName());
            post_seq_ocupantes.setText(" / "+ocupantes);
            post_seq_ocupanted.setText(" / "+ocupanted);*/


            //gridAltura.setSelection(PostHeight.parse(mPost.getHeight()).ordinal());
//            mHeight.setSelection(PostHeight.parse(mPost.getHeight()).ordinal());
            //      mEffort.setSelection(PostEffort.parse(mPost.getPostEffort()).ordinal());


          /*  ocupante_s.setSelection(OcupantesS.parse(mPost.getOcupante_s()).ordinal());
            ocupante_d.setSelection(OcupantesD.parse(mPost.getOcupante_d()).ordinal());*/

            //   mType.setSelection(mPost.getPostType());
        }



       /* view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verifier.rollBack(mPost.getGeoCode());
                dismissAllowingStateLoss();
            }
        });*/

        /*view.findViewById(R.id.location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsPickLocationActivity.class);
                if (PostEditDialogFragment.mCreating) {
                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, PostEditDialogFragment.mUserLatLng);
                    intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
                } else {
                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, mPost.getLocation().toLatLng());
                }
                startActivityForResult(intent, PICKUP_LOCATION);
            }
        });*/

        next_button_ponto_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                seq_lagradouro = mLogradouro.getText().toString();
                //               seq_numero = mNumero.getText().toString();

                //  seq_fase = mFase.getText().toString();
//                seq_etligacao = mETLigacao.getText().toString();
                //              seq_observacao = mObservacao.getText().toString();

              /*  if(!seq_lagradouro.equals("")){
                    post_seq_lagradouro.setText(seq_lagradouro.toUpperCase());
                }
                if(!seq_numero.equals("")){
                    post_seq_numero.setText("/"+seq_numero.toUpperCase());
                }*/
            /*if(!seq_complemento.equals("")){
                post_seq_complemento.setText("/"+seq_complemento.toUpperCase());
            }*/
             /*   if(!seq_fase.equals("")){
                    if(seq_fase.equals("SEM INFORMACAO")){
                        post_seq_fase.setText("/...");
                    }else{
                        post_seq_fase.setText(seq_fase.toUpperCase());
                    }
                }
                if(!seq_etligacao.equals("")){
                    post_seq_etligacao.setText("/"+seq_etligacao.toUpperCase());
                }*/
          /*      if(!seq_observacao.equals("")){
                    post_seq_observacao.setText("/"+seq_observacao.toUpperCase());
                }*/

                if (count[0] > fim) {
                                    mPontoEntrega.setTipoDemanda(seq_tipo_demanda);
                                    mPontoEntrega.setClassificacao(seq_classificacao);
                                    mPontoEntrega.setTipoImovel(seq_tipo_imovel);
                                    mPontoEntrega.setNumero_local(!seq_numero_local.equals("")? Integer.parseInt(seq_numero_local) : 0);
                                    mPontoEntrega.setNumero_andares_edificio(!seq_numero_andares_edificio.equals("")  ? Integer.parseInt(seq_numero_andares_edificio) : 0);
                                    mPontoEntrega.setNumero_total_apartamentos(!seq_numeto_total_apartamentos.equals("") ? Integer.parseInt(seq_numeto_total_apartamentos) : 0);
                                    mPontoEntrega.setNome_edificio(seq_nome_edifio);

                                    if (!edt_complemento1.getText().toString().equals("")) {
                                        mPontoEntrega.setComplemento1(edt_complemento1.getText().toString());
                                    } else {
                                        mPontoEntrega.setComplemento1(set_tipo_complemento1);
                                    }
                                    if (!edt_complemento2.getText().toString().equals("")) {
                                        mPontoEntrega.setComplemento2(edt_complemento2.getText().toString());
                                    } else {
                                        mPontoEntrega.setComplemento2(set_tipo_complemento2);
                                    }

                                    mPontoEntrega.setUpdate(true);
                                    mPontoEntrega.setPhotos(photosList);
                                    if (mPost != null) {
                                        mPontoEntrega.setPostId(mPost.getId());
                                    }


                                    //EquipmentUpdateUtils.setPontoAtualizacao(getContext(), mPost);
                                    Location l = mListener.getLastLocation();
                                    //mPontoEntrega.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                                    mPontoEntrega.setX_atualizacao(l.getLatitude());
                                    mPontoEntrega.setY_atualizacao(l.getLongitude());
                                    mPontoEntrega.setUpdate(true);

                                    final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                                    alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPontoEntrega.getGeoCode()));
                                    alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                                    alertDialog.setCancelable(false);
                                    alertDialog.show();

                                    //mPost.setObservations(ViewUtils.getTextViewValue(mObservations));
                                    //mPost.setHeight(altura);

                                    /*mPost.setOcupante_s(OcupantesS.getOcupantes_s().get(ocupante_s.getSelectedItemPosition()).intValue());
                                    mPost.setOcupante_d(OcupantesD.getOcupantesd().get(ocupante_d.getSelectedItemPosition()).intValue());*/

                                  /*  mPost.setOcupante_s(ocupantes);
                                    mPost.setOcupante_d(ocupanted);

                                    mPost.setPostEffort(esforco);
                                    mPost.setPostType(tipo);*/

                                 /*   mPost.setPostEffort(PostEffort.getNames().get(mEffort.getSelectedItemPosition()).toString());
                                    mPost.setPostType(mType.getSelectedItemPosition());
                                    mPost.setCreateDate(new Date());*/

                                   /* List<String> imagesTags = new ArrayList<String>();
                                    int count = mLayoutImages.getChildCount();
                                    for(int i = 0; i<count; i++) {
                                        LinearLayout ls = (LinearLayout) mLayoutImages.getChildAt(i);
                                        EditText e = (EditText) ls.getChildAt(0);
                                        imagesTags.add(String.valueOf(e.getText()));
                                    }*/
                                    mPontoEntrega.setPhotos(photosList);

                                    boolean workingOffline = FileUtils.serviceOrderFileExists(mPontoEntrega.getOrderId());
                                    if (mCreating) {
                                        mPontoEntrega.setClosed(true);
                                        mPontoEntrega.setUpdate(false);
                                        mPontoEntrega.setPostNumber(mListener.getNextPostNumber());

                                        LogUtils.log("highestPostNumber = " + mListener.getHighestPostNumber());
                                        mPontoEntrega.setLagradouro(lagradouro);

                                        if (workingOffline) {

                                            try {
                                                //OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
                                                OfflineDownloadResponse download = null;
                                                if(DownloadOrderOffline.getResponse() == null){
                                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mPontoEntrega.getOrderId()));
                                                    download = DownloadOrderOffline.getResponse();
                                                }
                                                else{
                                                    download = DownloadOrderOffline.getResponse();
                                                }

                                                List<PontoEntrega> posts = download.getPontoEntregaList();
                                                List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList() == null ? new ArrayList<VaosPontoPoste>() : download.getVaosPontoPosteList();
                                                if (vaosPontoPostes == null) {
                                                    vaosPontoPostes = new ArrayList<>();
                                                }

                                                // vaosDeletar = new ArrayList<>();
                                               /* if(mPontoEntrega.getId() != 0){
                                                    long id_ponto = mPontoEntrega.getId();
                                                    for(VaosPontoPoste vaosPontoPoste : vaosPontoPostes){
                                                        if(vaosPontoPoste.getId_ponto_entrega() == id_ponto){
                                                            vaosDeletar = vaosPontoPoste;
                                                            return;
                                                        }
                                                    }
                                                }*/

                                                mPontoEntrega.setId(-1 * System.currentTimeMillis());
                                                mVaosPontoPoste = new VaosPontoPoste();
                                                mVaosPontoPoste.setId(-1 * System.currentTimeMillis());
                                                mVaosPontoPoste.setId_ponto_entrega(mPontoEntrega.getId());
                                                mVaosPontoPoste.setId_poste(mPost.getId());
                                                mVaosPontoPoste.setId_ordem_servico(mPontoEntrega.getOrderId());



                                               /* SharedPreferences preferences = getActivity().getSharedPreferences("poste", Context.MODE_PRIVATE);
                                                String lat = preferences.getString("lat", null);
                                                String lon = preferences.getString("lon", null);*/


                                                if (mPost != null) {
                                                    mVaosPontoPoste.setX1(mPontoEntrega.getX());
                                                    mVaosPontoPoste.setX2(mPost.getLocation().getLat());
                                                    mVaosPontoPoste.setY1(mPontoEntrega.getY());
                                                    mVaosPontoPoste.setY2(mPost.getLocation().getLon());
                                                }
                                                vaosPontoPostes.add(mVaosPontoPoste);
                                                posts.add(mPontoEntrega);
                                                download.setVaosPontoPosteList(vaosPontoPostes);
                                                deletarPlyline = false;
                                                download.setPontoEntregaList(posts);

                                                if (FileUtils.saveOfflineDownload(download)) {
                                                    Verifier.clearRollBackStack();
                                                    Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                    Toast.makeText(getActivity(),
                                                            R.string.dialog_post_edit_saving_success,
                                                            Toast.LENGTH_LONG).show();
                                                    dismissAllowingStateLoss();
                                                    mListener.onPontoEntegasChanged(mPontoEntrega, true, mPost, deletarPlyline, true);

                                                } else {
                                                    mPontoEntrega.setClosed(false);
                                                    Toast.makeText(getActivity(),
                                                            getString(R.string.dialog_post_edit_saving_error,
                                                                    mPontoEntrega.getId()),
                                                            Toast.LENGTH_LONG).show();
                                                }

                                                alertDialog.dismiss();

                                            } catch (IOException e) {
                                                mPontoEntrega.setClosed(false);
                                                LogUtils.error(this, e);
                                                alertDialog.dismiss();
                                                Toast.makeText(getActivity(),
                                                        getString(R.string.dialog_post_edit_saving_error,
                                                                mPontoEntrega.getId()),
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        } else {

                                            PontoEntregaController.get().create(getContext(), mPontoEntrega, new VisiumApiCallback<PontoEntrega>() {
                                                @Override
                                                public void callback(PontoEntrega pontoEntrega, ErrorResponse e) {
                                                    alertDialog.dismiss();

                                                    if (e == null && pontoEntrega != null) {
                                                        Verifier.clearRollBackStack();
                                                        Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_success,
                                                                Toast.LENGTH_LONG).show();
                                                        dismissAllowingStateLoss();
                                                        mListener.onPontoEntegasChanged(pontoEntrega, true, mPost, deletarPlyline, true);
                                                    } else if (e != null && e.isCustomized()) {
                                                        mPontoEntrega.setClosed(false);
                                                        Toast.makeText(getActivity(),
                                                                e.getMessage(), Toast.LENGTH_LONG).show();
                                                    } else {
                                                        mPontoEntrega.setClosed(false);
                                                        Toast.makeText(getActivity(),
                                                                getString(R.string.dialog_post_edit_saving_error,
                                                                        mPontoEntrega.getId()),
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }

                                    } else {
                                        //mPost.setClosed(mTransaction.isChecked());

                                        if (workingOffline) {

                                            try {
                                                //OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
                                                OfflineDownloadResponse download = null;
                                                if(DownloadOrderOffline.getResponse() == null){
                                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mPontoEntrega.getOrderId()));
                                                    download = DownloadOrderOffline.getResponse();
                                                }
                                                else{
                                                    download = DownloadOrderOffline.getResponse();
                                                }
                                                List<PontoEntrega> pontoEntregas = download.getPontoEntregaList();
                                                List<Post> postes = download.getPosts();
                                                List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList() == null ? new ArrayList<VaosPontoPoste>() : download.getVaosPontoPosteList();
                                                Iterator<PontoEntrega> iterator = pontoEntregas.iterator();

                                                while (iterator.hasNext()) {
                                                    PontoEntrega post = iterator.next();
                                                    if (post.getId() == mPontoEntrega.getId()) {
                                                        iterator.remove();
                                                        break;
                                                    }
                                                }

                                                for (Post post : postes){
                                                    if(post.getId() == mPontoEntrega.getPostId()){
                                                        mPost = post;
                                                    }
                                                }
                                                if (mPost != null) {
                                                    if (mPontoEntrega.getId() != 0) {
                                                        long id_ponto = mPontoEntrega.getId();
                                                        for (VaosPontoPoste vaosPontoPoste : vaosPontoPostes) {
                                                            if (vaosPontoPoste.getId_ponto_entrega() == id_ponto) {
                                                                vaosDeletar = vaosPontoPoste;
                                                                vaosPontoPostes.remove(vaosPontoPoste);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }

                                             /*   mVaosPontoPoste = new VaosPontoPoste();
                                                if (mPost != null) {
                                                    deletarPlyline = true;
                                                    //mVaosPontoPoste = new VaosPontoPoste();
                                                    mVaosPontoPoste.setId_ponto_entrega(mPontoEntrega.getId());
                                                    mVaosPontoPoste.setX1(mPontoEntrega.getX());
                                                    mVaosPontoPoste.setX2(mPost.getLocation().getLat());
                                                    mVaosPontoPoste.setY1(mPontoEntrega.getY());
                                                    mVaosPontoPoste.setY2(mPost.getLocation().getLon());
                                                    mVaosPontoPoste.setId_poste(mPost.getId());
                                                }*/
                                             /*   if (trocaPositionVaoPOnto) {
                                                    if (mPontoEntrega.getId() != 0) {
                                                        deletarPlyline = true;
                                                        long id_ponto = mPontoEntrega.getId();
                                                        for (VaosPontoPoste vaosPontoPoste : vaosPontoPostes) {
                                                            if (vaosPontoPoste.getId_ponto_entrega() == id_ponto) {
                                                                //vaosDeletar = vaosPontoPoste;
                                                                //vaosPontoPostes.remove(vaosPontoPoste);
                                                                // mVaosPontoPoste.setId_ponto_entrega(mPontoEntrega.getId());
                                                                vaosPontoPoste.setX1(mPontoEntrega.getX());
                                                                // vaosPontoPoste.setX2(mPost.getLocation().getLat());
                                                                mPost = new Post();
                                                                vaosPontoPoste.setY1(mPontoEntrega.getY());
                                                                PostLocation postLocation = new PostLocation();
                                                                postLocation.setLat(vaosPontoPoste.getX2());
                                                                postLocation.setLon(vaosPontoPoste.getY2());
                                                                mPost.setLocation(postLocation);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                vaosPontoPostes.add(mVaosPontoPoste);*/

                                                pontoEntregas.add(mPontoEntrega);
                                                download.setPontoEntregaList(pontoEntregas);
                                             //   download.setVaosPontoPosteList(vaosPontoPostes);

                                                if (FileUtils.saveOfflineDownload(download)) {
                                                    Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                    Toast.makeText(getActivity(),
                                                            R.string.dialog_post_edit_saving_success,
                                                            Toast.LENGTH_LONG).show();
                                                    dismissAllowingStateLoss();
                                                    mListener.onPontoEntegasChanged(mPontoEntrega, false, mPost, deletarPlyline, true);

                                                } else {
                                                    Toast.makeText(getActivity(),
                                                            getString(R.string.dialog_post_edit_saving_error,
                                                                    mPontoEntrega.getId()),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                                alertDialog.dismiss();

                                            } catch (IOException e) {
                                                LogUtils.error(this, e);
                                                alertDialog.dismiss();

                                                Toast.makeText(getActivity(),
                                                        getString(R.string.dialog_post_edit_saving_error,
                                                                mPontoEntrega.getId()),
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            PontoEntregaController.get().save(getContext(), mPontoEntrega, new VisiumApiCallback<PontoEntrega>() {
                                                @Override
                                                public void callback(PontoEntrega pontoEntrega, ErrorResponse e) {
                                                    alertDialog.dismiss();

                                                    if (e == null && pontoEntrega != null) {
                                                        Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_success,
                                                                Toast.LENGTH_LONG).show();
                                                        dismissAllowingStateLoss();
                                                        mListener.onPontoEntegasChanged
                                                                (mPontoEntrega, false, mPost, deletarPlyline, true);
                                                    } else if (e != null && e.isCustomized()) {
                                                        Toast.makeText(getActivity(),
                                                                e.getMessage(), Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_error,
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    }


                } else {
                    if(post_seq_tipoDemanda.getText().toString().equals("EDIFÍCIO")){
                        podePassar = false;
                    }else{
                        podePassar = true;
                    }
                    viewFlipper.showNext();
                    if (count[0] == 1) {
                            seq_numero = edt_numero_local.getText().toString().toUpperCase();
                            seq_numero_local = edt_numero_local.getText().toString().toUpperCase();
                            post_seq_nunero_local.setText("/" + seq_numero_local);
                        if(podePassar){
                            viewFlipper.showNext();
                            viewFlipper.showNext();
                            viewFlipper.showNext();
                            ++count[0];
                            ++count[0];
                            ++count[0];
                        }
                    }
                    if(!podePassar){
                        if (count[0] == 2) {
                            seq_numero_andares_edificio = edt_numero_andar_edificio.getText().toString().toUpperCase();
                            post_seq_numero_andares_edificio.setText("/" + seq_numero_andares_edificio);
                        }
                        if (count[0] == 3) {
                            seq_numeto_total_apartamentos = edt_numero_total_apartamentos.getText().toString().toUpperCase();
                            post_seq_numero_total_apartamentos.setText("/" + seq_numeto_total_apartamentos);
                        }
                        if (count[0] == 4) {
                            seq_nome_edifio = edt_nome_edificio.getText().toString().toUpperCase();
                            post_seq_nome_edificio.setText("/" + seq_nome_edifio);
                        }
                    }

                    if (count[0] == 7) {
                        if (!edt_complemento1.getText().toString().equals("")) {
                            post_seq_complemento1.setText("/" + edt_complemento1.getText().toString().toUpperCase());
                        }
                    }
                    if (count[0] == 8) {
                        if (!edt_complemento2.getText().toString().equals("")) {
                            post_seq_complemento2.setText("/" + edt_complemento2.getText().toString().toUpperCase());
                        }
                    }

                    count[0]++;
                    if (count[0] > fim) {
                        next_button_ponto_entrega.setText("Salvar");
                        Drawable image = ContextCompat.getDrawable(getContext(), R.drawable.ic_check_black_24dp);
                        image.setBounds(0, 0, 60, 60);
                        next_button_ponto_entrega.setCompoundDrawables(null, null, image, null);
                    } else {

                    }
                }
            }
        });

        back_button_ponto_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count[0] > 0) {
                    if(count[0] == 5){
                        if(podePassar) {
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            --count[0];
                            --count[0];
                            --count[0];
                        }
                    }
                    viewFlipper.showPrevious();
                    --count[0];
                    next_button_ponto_entrega.setText("Próximo");
                    Drawable image = ContextCompat.getDrawable(getContext(), R.drawable.ic_navigate_next_black_24dp);
                    image.setBounds(0, 0, 60, 60);
                    next_button_ponto_entrega.setCompoundDrawables(null, null, image, null);
                } else {
                    Verifier.rollBack(mPontoEntrega.getGeoCode());
                    dismissAllowingStateLoss();
                }
            }
        });

/*
         List<String> imagesTags = new ArrayList<String>();
        int count = mLayoutImages.getChildCount();
        for(int i = 0; i<count; i++) {
          LinearLayout l = (LinearLayout) mLayoutImages.getChildAt(i);
         EditText e = (EditText) l.getChildAt(0);
        imagesTags.add(String.valueOf(e.getText()));
         }

        mPontoEntrega.setPhotos(photosList);*/

     /*   view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EquipmentUpdateUtils.setPontoAtualizacao(getContext(), mPost);
                Location l = mListener.getLastLocation();
                mPost.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
                mPost.setUpdate(true);

                final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPost.getGeoCode()));
                alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                alertDialog.setCancelable(false);
                alertDialog.show();

                mPost.setObservations(ViewUtils.getTextViewValue(mObservations));
                mPost.setHeight(PostHeight.getHeights().get(mHeight.getSelectedItemPosition()).doubleValue());

                mPost.setOcupante_s(OcupantesS.getOcupantes_s().get(ocupante_s.getSelectedItemPosition()).intValue());
                mPost.setOcupante_d(OcupantesD.getOcupantesd().get(ocupante_d.getSelectedItemPosition()).intValue());

                mPost.setPostEffort(PostEffort.getNames().get(mEffort.getSelectedItemPosition()).toString());
                mPost.setPostType(mType.getSelectedItemPosition());
                mPost.setCreateDate(new Date());

               // List<String> imagesTags = new ArrayList<String>();
                //int count = mLayoutImages.getChildCount();
                //for(int i = 0; i<count; i++) {
                  //  LinearLayout l = (LinearLayout) mLayoutImages.getChildAt(i);
                   // EditText e = (EditText) l.getChildAt(0);
                    //imagesTags.add(String.valueOf(e.getText()));
               // }

                mPost.setPhotos(photosList);

                boolean workingOffline = FileUtils.serviceOrderFileExists(mPost.getOrderId());
                if (mCreating) {
                    mPost.setClosed(true);
                    mPost.setUpdate(true);
                    mPost.setPostNumber(mListener.getNextPostNumber());
                    LogUtils.log("highestPostNumber = " + mListener.getHighestPostNumber());

                    if (workingOffline) {

                        try {
                            OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                            List<Post> posts = download.getPosts();
                            mPost.setId(-1 * System.currentTimeMillis());
                            posts.add(mPost);
                            download.setPostList(posts);

                            if (FileUtils.saveOfflineDownload(download)) {
                                Verifier.clearRollBackStack();
                                Verifier.addPostCount(mPost.getGeoCode());
                                Toast.makeText(getActivity(),
                                        R.string.dialog_post_edit_saving_success,
                                        Toast.LENGTH_LONG).show();
                                dismissAllowingStateLoss();
                                mListener.onPostChanged(mPost, true);

                            } else {
                                mPost.setClosed(false);
                                Toast.makeText(getActivity(),
                                        getString(R.string.dialog_post_edit_saving_error,
                                                mPost.getId()),
                                        Toast.LENGTH_LONG).show();
                            }

                            alertDialog.dismiss();

                        } catch (IOException e) {
                            mPost.setClosed(false);
                            LogUtils.error(this, e);
                            alertDialog.dismiss();
                            Toast.makeText(getActivity(),
                                    getString(R.string.dialog_post_edit_saving_error,
                                            mPost.getId()),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                    else {

                        PostController.get().create(getContext(), mPost, new VisiumApiCallback<Post>() {
                            @Override
                            public void callback(Post post, ErrorResponse e) {
                                alertDialog.dismiss();

                                if (e == null && post != null) {
                                    Verifier.clearRollBackStack();
                                    Verifier.addPostCount(mPost.getGeoCode());
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_success,
                                            Toast.LENGTH_LONG).show();
                                    dismissAllowingStateLoss();
                                    mListener.onPostChanged(post, true);
                                } else if (e != null && e.isCustomized()) {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            getString(R.string.dialog_post_edit_saving_error,
                                                    mPost.getId()),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                } else {
                    //mPost.setClosed(mTransaction.isChecked());

                    if (workingOffline) {

                        try {
                            OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                            List<Post> posts = download.getPosts();

                            Iterator<Post> iterator = posts.iterator();

                            while (iterator.hasNext()) {
                                Post post = iterator.next();
                                if (post.getId() == mPost.getId()) {
                                    iterator.remove();
                                    break;
                                }
                            }

                            posts.add(mPost);
                            download.setPostList(posts);

                            if (FileUtils.saveOfflineDownload(download)) {
                                Verifier.addPostCount(mPost.getGeoCode());
                                Toast.makeText(getActivity(),
                                        R.string.dialog_post_edit_saving_success,
                                        Toast.LENGTH_LONG).show();
                                dismissAllowingStateLoss();
                                mListener.onPostChanged(mPost, false);

                            } else {

                                Toast.makeText(getActivity(),
                                        getString(R.string.dialog_post_edit_saving_error,
                                                mPost.getId()),
                                        Toast.LENGTH_LONG).show();
                            }

                            alertDialog.dismiss();

                        } catch (IOException e) {
                            LogUtils.error(this, e);
                            alertDialog.dismiss();

                            Toast.makeText(getActivity(),
                                    getString(R.string.dialog_post_edit_saving_error,
                                            mPost.getId()),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        PostController.get().save(getContext(), mPost, new VisiumApiCallback<Post>() {
                            @Override
                            public void callback(Post post, ErrorResponse e) {
                                alertDialog.dismiss();

                                if (e == null && post != null) {
                                    Verifier.addPostCount(mPost.getGeoCode());
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_success,
                                            Toast.LENGTH_LONG).show();
                                    dismissAllowingStateLoss();
                                    mListener.onPostChanged(mPost, false);
                                } else if (e != null && e.isCustomized()) {
                                    Toast.makeText(getActivity(),
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_error,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        });*/


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        SharedPreferences preferences = getActivity().getSharedPreferences("poste", Context.MODE_PRIVATE);
//
//        final List<PostType> list_type = Arrays.asList(PostType.values());
//
//        int tipo = preferences.getInt("tipo", 0);
//
////        tipo_text.setText(list_type.get(tipo).toString());
//  //      obs_text.setText(preferences.getString("obs", null));
//    //    altura_text.setText(preferences.getString("altura", null));
//        String lat = preferences.getString("lat", null);
//        String lon = preferences.getString("lon", null);
//        long id_poste = preferences.getLong("id", 0);
//
//        if (lon != null) {
//            mPost = new Post();
//
//            PostLocation postLocation = new PostLocation();
//            postLocation.setLat(Double.valueOf(lat));
//            postLocation.setLon(Double.valueOf(lon));
//            mPost.setId(id_poste);
//
//            mPost.setLocation(postLocation);
//        }
//
//        SharedPreferences.Editor preferencesLimpo = getActivity().getSharedPreferences("poste", Context.MODE_PRIVATE).edit();
//        preferencesLimpo.clear();
//        preferencesLimpo.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKUP_LOCATION) {

            if (data != null && data.hasExtra(MapsPickLocationActivity.PICK_LOCATION)) {
                LatLng latLng = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATION);
                LogUtils.log("onMapPickLocationActivityResult");
                mPontoEntrega.setX(latLng.latitude);
                mPontoEntrega.setY(latLng.longitude);
                //mPontoEntrega.setLocation(new PontoEntregaLocation(latLng.latitude, latLng.longitude));
                //mLat.setText(String.valueOf(latLng.latitude));
                //mLon.setText(String.valueOf(latLng.longitude));
            } else {
                Toast.makeText(getActivity(),
                        R.string.dialog_post_edit_pickup_location_error,
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}