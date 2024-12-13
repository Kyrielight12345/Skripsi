function toggleTable(tableId) {
  document.getElementById("tabel-spp").style.display = "none";
  document.getElementById("tabel-santri").style.display = "none";
  document.getElementById("tabel-pengajar").style.display = "none";

  if ($.fn.dataTable.isDataTable("#table-santri")) {
    $("#table-santri").DataTable().clear().destroy();
  }
  if ($.fn.dataTable.isDataTable("#table-pengajar")) {
    $("#table-pengajar").DataTable().clear().destroy();
  }
  if ($.fn.dataTable.isDataTable("#table-spp")) {
    $("#table-spp").DataTable().clear().destroy();
  }

  if (tableId === "spp") {
    document.getElementById("tabel-spp").style.display = "block";

    if (!$.fn.dataTable.isDataTable("#table-spp")) {
      $.ajax({
        method: "GET",
        url: window.location.origin + "/api/spp",
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

          $("#table-spp").DataTable({
            data: data,
            columns: [
              {
                data: null,
                render: (data, type, row, meta) => {
                  return meta.row + 1;
                },
              },
              { data: "santri.name" },
              { data: "santri.jilid.name" },
              { data: "tanggal_bayar" },
              { data: "jumlah_bayar" },
              {
                data: null,
                render: (data, type, row) => {
                  const bulanNama = bulan[row.bayar_bulan - 1];
                  return `${bulanNama} ${row.bayar_tahun}`;
                },
              },
              { data: "pengajar.name" },
            ],
            destroy: true,
            paging: true,
            responsive: true,
          });
        },
        error: function (xhr, status, error) {
          console.error("Error fetching data:", error);
          alert("Failed to load data.");
        },
      });
    }
  } else if (tableId === "santri") {
    document.getElementById("tabel-santri").style.display = "block";

    if (!$.fn.dataTable.isDataTable("#table-santri")) {
      $("#table-santri").DataTable({
        ajax: {
          method: "GET",
          url: "api/santri",
          dataSrc: "",
        },
        columns: [
          {
            data: null,
            render: (data, row, type, meta) => {
              return meta.row + 1;
            },
          },
          { data: "name" },
          { data: "alamat" },
          {
            data: null,
            render: (data, type, row) => {
              return `${row.tempat_lahir} / ${row.tanggal_lahir}`;
            },
          },
          { data: "jilid.name" },
          { data: "tanggal_bergabung" },
        ],
      });
    }
  } else if (tableId === "pengajar") {
    document.getElementById("tabel-pengajar").style.display = "block";

    if (!$.fn.dataTable.isDataTable("#table-pengajar")) {
      $("#table-pengajar").DataTable({
        ajax: {
          method: "GET",
          url: "api/pengajar",
          dataSrc: "",
        },
        columns: [
          {
            data: null,
            render: (data, row, type, meta) => {
              return meta.row + 1;
            },
          },
          { data: "name" },
          { data: "alamat" },
          {
            data: null,
            render: (data, type, row) => {
              return `${row.tempat_lahir} / ${row.tanggal_lahir}`;
            },
          },
          { data: "kelas.name" },
          { data: "tanggal_bergabung" },
        ],
      });
    }
  }
}

function downloadPDF(tableId) {
  const table = document.getElementById(tableId);
  const options = {
    margin: 1,
    filename: tableId + "-report.pdf",
    image: { type: "jpeg", quality: 0.98 },
    html2canvas: { scale: 4, useCORS: true },
    jsPDF: { unit: "in", format: "letter", orientation: "portrait" },
  };

  const style = document.createElement("style");
  style.innerHTML = `
      table {
          font-family: Arial, sans-serif;
          border-collapse: collapse;
          width: 100%;
          color: black;
          font-color: black;
      }
      th, td {
          border: 1px solid #000;
          padding: 8px;
          text-align: center;
      }
      th {
          background-color: #f2f2f2;
          color: black;
          font-color: black;
      }
      td {
          color: black;
      }
  `;
  document.head.appendChild(style);

  html2pdf().from(table).set(options).save();
}
