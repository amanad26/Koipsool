package com.koipsool_new.ui.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.koipsool_new.HelpAndSuuportActivity;
import com.koipsool_new.MoreActivity;
import com.koipsool_new.R;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityHomeBinding;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Session session;

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(this);

        binding.icMenu.setOnClickListener(view -> binding.drawerLayout.open());

        binding.userName.setText(session.getUserName());

        binding.changePhoto.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.profileLinear.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.AGMybill.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyBillingActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.navPostinternshipJob.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, PostMrInternshipJobActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.navTowService.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, HelpAndSuuportActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.clients.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ClientsActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.applications.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ApplicationPharmaActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.more.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MoreActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.coupons.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CouponsActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.myCoupons.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyCouponsActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.addproduct.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddProductActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.myProduct.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyProductActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.myJobandinternships.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyJobsAndInternshipsActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.medicalRepresntative.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MedicalRepresentativesActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.navMyTeam.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyTeamActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.addMembers.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddMembersActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });
        binding.txtReports.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MadicalReportActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });
        binding.createbill.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CreateBillActivity.class);
            startActivity(intent);
            binding.drawerLayout.close();
        });

        binding.spinnerImg.setOnClickListener(view -> {
            if (binding.linear1.getVisibility() == View.VISIBLE) {
                binding.linear1.setVisibility(View.GONE);
                binding.billImage.setImageResource(R.drawable.bottomlist);
            } else {
                binding.billImage.setImageResource(R.drawable.ic_arrow_up);
                binding.linear1.setVisibility(View.VISIBLE);
            }
        });

        binding.spinnerProduct.setOnClickListener(view -> {
            if (binding.linear4.getVisibility() == View.VISIBLE) {
                binding.linear4.setVisibility(View.GONE);
                binding.productImage.setImageResource(R.drawable.bottomlist);
            } else {
                binding.productImage.setImageResource(R.drawable.ic_arrow_up);
                binding.linear4.setVisibility(View.VISIBLE);
            }
        });

        binding.spinnersImg.setOnClickListener(view -> {
            if (binding.linear2.getVisibility() == View.VISIBLE) {
                binding.linear2.setVisibility(View.GONE);
                binding.teamMemberImage.setImageResource(R.drawable.bottomlist);
            } else {
                binding.teamMemberImage.setImageResource(R.drawable.ic_arrow_up);
                binding.linear2.setVisibility(View.VISIBLE);
            }
        });

        binding.bottom2.setOnClickListener(view -> {
            if (binding.linear3.getVisibility() == View.VISIBLE) {
                binding.linear3.setVisibility(View.GONE);
                binding.bottom2.setImageResource(R.drawable.bottomlist);
            } else {
                binding.bottom2.setImageResource(R.drawable.ic_arrow_up);
                binding.linear3.setVisibility(View.VISIBLE);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new HomeFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!session.getImage().equalsIgnoreCase("")) {
            Picasso.get().load(session.getImage()).placeholder(R.drawable.profile).into(binding.changePhoto);
        }
    }
}