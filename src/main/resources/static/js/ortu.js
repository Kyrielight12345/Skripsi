document.addEventListener("DOMContentLoaded", function () {
  const santriLink = document.getElementById("santriLink");
  const progressLink = document.getElementById("progressLink");
  const sppLink = document.getElementById("sppLink");
  const nilaiLink = document.getElementById("progressLink");

  if (santriLink) {
    santriLink.addEventListener("click", function (event) {
      event.preventDefault();

      window.location.href = "/santri/detail";

      getDetailSantri();
    });
  } else {
    console.error("santriLink element not found.");
  }

  if (progressLink) {
    progressLink.addEventListener("click", function (event) {
      event.preventDefault();

      window.location.href = "/progress/detail-ortu";

      loadProgressData();
    });
  } else {
    console.error("progressLink element not found.");
  }

  if (sppLink) {
    sppLink.addEventListener("click", function (event) {
      event.preventDefault();

      window.location.href = "/spp/detail-ortu";

      spp();
    });
  } else {
    console.error("progressLink element not found.");
  }

  if (nilaiLink) {
    sppLink.addEventListener("click", function (event) {
      event.preventDefault();

      window.location.href = "/nilai/detail-ortu";

      nilaispp();
    });
  } else {
    console.error("progressLink element not found.");
  }

  const currentPath = window.location.pathname;
  if (currentPath.includes("/progress/detail-ortu")) {
    loadProgressData();
  } else if (currentPath.includes("/santri/detail")) {
    getDetailSantri();
  } else if (currentPath.includes("/spp/detail-ortu")) {
    spp();
  } else if (currentPath.includes("/nilai/detail-ortu")) {
    nilai();
  }
});

function getDetailSantri() {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/santri",
    dataType: "JSON",
    contentType: "application/json",
    success: function (response) {
      console.log(response);

      const data =
        Array.isArray(response) && response.length > 0 ? response[0] : null;

      if (!data) {
        console.error("Data tidak tersedia.");
        return;
      }

      var ttl = data.tempat_lahir + "/" + data.tanggal_lahir;
      $("#nama").text(data.name);
      $("#jilid").text(data.jilid.name);
      $("#kelas").text(data.jilid.kelas.name);
      $("#alamat").text(data.alamat);
      $("#ttl").text(ttl);
      $("#gender").text(data.jenisKelamin);
      $("#masuk").text(data.tanggal_bergabung);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function loadProgressData() {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/progress",
    dataType: "JSON",
    success: function (data) {
      console.log("Fetched data:", data);

      if ($.fn.DataTable.isDataTable("#tabel-detail-progress-ortu")) {
        $("#tabel-detail-progress-ortu")
          .DataTable()
          .clear()
          .rows.add(data)
          .draw();
      } else {
        $("#tabel-detail-progress-ortu").DataTable({
          data: data,
          columns: [
            {
              data: null,
              render: (data, type, row, meta) => {
                return meta.row + 1;
              },
            },
            {
              data: "tanggal_progress",
            },
            {
              data: "halaman",
            },
            {
              data: "keterangan",
            },
          ],
          destroy: true,
          responsive: true,
        });
      }
    },
    error: function (xhr, status, error) {
      console.error("Error fetching data:", error);
      alert("Failed to load progress data.");
    },
  });
}

function spp() {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/spp/",
    success: function (data) {
      console.log("Fetched data:", data);

      const bulan = [
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember",
      ];

      $("#tabel-detail-spp-ortu").DataTable({
        data: data,
        columns: [
          {
            data: null,
            render: (data, type, row, meta) => {
              return meta.row + 1;
            },
          },
          {
            data: "tanggal_bayar",
          },
          {
            data: "jumlah_bayar",
          },
          {
            data: null,
            render: (data, type, row) => {
              const bulanNama = bulan[row.bayar_bulan - 1];
              return `${bulanNama} ${row.bayar_tahun}`;
            },
          },
          {
            data: "pengajar.name",
          },
        ],
      });
    },
    error: function (xhr, status, error) {
      console.error("Error fetching data:", error);
      alert("Failed to load data.");
    },
  });
}

function nilai() {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/nilai",
    success: function (data) {
      if ($.fn.DataTable.isDataTable("#tabel-detail-nilai-ortu")) {
        const table = $("#tabel-detail-nilai-ortu").DataTable();
        table.clear();
        table.rows.add(data);
        table.draw();
      } else {
        $("#tabel-detail-nilai-ortu").DataTable({
          data: data,
          columns: [
            {
              data: null,
              render: (data, type, row, meta) => {
                return meta.row + 1;
              },
            },
            { data: "al_harakat" },
            { data: "makhraj" },
            { data: "imla" },
            { data: "an_namroh" },
            { data: "al_lafadz" },
            { data: "tajwid" },
            { data: "gharib" },
            { data: "al_mad" },
            { data: "tanggal_ujian" },
          ],
          destroy: true,
          responsive: true,
        });
      }
    },
    error: function (xhr, status, error) {
      console.error("Error fetching data:", error);
      alert("Failed to load data.");
    },
  });
}
