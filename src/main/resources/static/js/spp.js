var role = document.getElementById("role").getAttribute("data-role");
console.log(role);

$(document).ready(function () {
  $("#tabel-spp").DataTable({
    ajax: {
      method: "GET",
      url: "api/spp/santri",
      dataSrc: function (data) {
        console.log("Received Data: ", data);

        return Object.keys(data).map((key) => data[key][0]);
      },
    },
    columns: [
      {
        data: null,
        render: (data, type, row, meta) => {
          return meta.row + 1;
        },
      },
      {
        data: "santri.name",
      },
      {
        data: "santri.jilid.name",
      },
      {
        data: null,
        render: (data) => {
          return `
              <div class="d-flex gap-3 justify-content-center">
                <button
                  type="button"
                  class="btn btn-primary"
                  onclick="navigateAndFetch(${data.santri.id})"
                >
                  Detail
                </button>
              </div>
            `;
        },
      },
    ],
  });
  fetch(window.location.origin + "/api/santri")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok " + response.statusText);
      }
      return response.json();
    })
    .then((data) => {
      console.log("Santri Data: ", data);
      const santriDropdown = document.getElementById("create-santri");
      santriDropdown.innerHTML = "";

      data.forEach((santri) => {
        const option = document.createElement("option");
        option.value = santri.id;
        option.textContent = santri.name;
        santriDropdown.appendChild(option);
      });
    })
    .catch((error) => {
      console.error("Error fetching santri data: ", error);
    });
});

function navigateAndFetch(id) {
  window.location.href = `/spp/detail?id=${id}`;
}

function navigateUpdate(id) {
  const url = `/spp/update?id=${id}`;
  window.location.href = `/spp/update?id=${id}`;

  window.history.pushState({ id }, "", url);
}

function getById(id) {
  console.log("Fetching details for ID:", id);

  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/spp/santri/" + id,
    success: function (data) {
      console.log("Fetched data:", data);

      $("#nama").text(data[0].santri.name);
      $("#jilid").text(data[0].santri.jilid.name);

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

      $("#tabel-detail-spp").DataTable({
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
          {
            data: null,
            render: (data) => {
              if (role == "[ROLE_PENGAJAR]") {
                return `
                <div class="d-flex gap-3 justify-content-center">
                    </button>
                <!-- Button trigger modal update -->
                <button
                  type="button"
                  class="btn btn-warning"
                    class="btn btn-warning"
                     onclick="navigateUpdate(${data.id})"
                >
                  Update
                </button>
                <!-- Button trigger modal delete -->
                <button
                  type="button"
                  class="btn btn-danger"
                  onclick="deleteSpp(${data.id})"
                >
                  Delete
                </button>
                </div>
              `;
              } else {
                return "-";
              }
            },
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

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get("id");

  if (id) {
    const path = window.location.pathname;

    if (path.includes("/spp/detail")) {
      getById(id);
    } else if (path.includes("/spp/update")) {
      beforeUpdate(id);
    } else {
      console.error("Tidak ada tindakan yang sesuai untuk path:", path);
    }
  } else {
    console.error("ID tidak ditemukan di URL");
  }
});

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/spp/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      $("#create-bayar").val(data.tanggal_bayar);
      $("#create-bulan").val(data.bayar_bulan);
      $("#create-tahun").val(data.bayar_tahun);
      $("#edit-santri").val(data.santri.id);
      $("#create-spp-id").val(data.id);
    },
    error: function (err) {
      console.log(err);
    },
  });
}

// $("#form-spp").on("submit", (event) => {
//   event.preventDefault();

//   let sppData = {
//     tanggal_bayar: $("#create-bayar").val(),
//     jumlah_bayar: $("#create-jumlah").val(),
//     bayar_bulan: $("#create-bulan").val(),
//     bayar_tahun: $("#create-tahun").val(),
//     santri: {
//       id: $("#create-santri").val(),
//     },
//     pengajar: {
//       id: 1,
//     },
//   };

//   console.log("Data yang akan dikirim:", sppData);

//   $.ajax({
//     method: "POST",
//     url: window.location.origin + "/api/spp/create",
//     dataType: "JSON",
//     contentType: "application/json",
//     beforeSend: addCSRFToken(),
//     data: JSON.stringify(sppData),
//     success: (res) => {
//       Swal.fire({
//         position: "center",
//         icon: "success",
//         title: "Data SPP berhasil disimpan!",
//         showConfirmButton: false,
//         timer: 2000,
//       }).then(() => {
//         window.location.href = window.location.origin + "/spp";
//       });
//     },
//     error: (err) => {
//       Swal.fire({
//         position: "center",
//         icon: "error",
//         title: "Terjadi kesalahan saat menyimpan data SPP!",
//         showConfirmButton: false,
//         timer: 2000,
//       });
//       console.error("Error:", err);
//     },
//   });
// });

$("#form-spp").on("submit", (event) => {
  event.preventDefault();

  let jumlahBayar = parseInt($("#create-jumlah").val());
  let bayarBulan = parseInt($("#create-bulan").val());
  let bayarTahun = parseInt($("#create-tahun").val());
  let santriId = $("#create-santri").val();

  if (jumlahBayar >= 10000) {
    let iterations = Math.floor(jumlahBayar / 10000);

    const processInsert = (index) => {
      if (index >= iterations) {
        window.location.href = `${window.location.origin}/spp`;
        return;
      }

      let sppData = {
        tanggal_bayar: $("#create-bayar").val(),
        jumlah_bayar: 10000,
        bayar_bulan: bayarBulan,
        bayar_tahun: bayarTahun,
        santri: { id: santriId },
        pengajar: { id: 1 },
      };

      $.ajax({
        method: "POST",
        url: `${window.location.origin}/api/spp/create`,
        dataType: "JSON",
        contentType: "application/json",
        beforeSend: addCSRFToken(),
        data: JSON.stringify(sppData),
        success: (res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Data SPP berhasil disimpan!",
            showConfirmButton: false,
            timer: 1000,
          });

          bayarBulan++;
          if (bayarBulan > 12) {
            bayarBulan = 1;
            bayarTahun++;
          }

          processInsert(index + 1);
        },
        error: (err) => {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "Terjadi kesalahan saat menyimpan data SPP!",
            showConfirmButton: false,
            timer: 2000,
          });
          console.error("Error:", err);
        },
      });
    };

    processInsert(0);
  } else {
    let sppData = {
      tanggal_bayar: $("#create-bayar").val(),
      jumlah_bayar: jumlahBayar,
      bayar_bulan: bayarBulan,
      bayar_tahun: bayarTahun,
      santri: { id: santriId },
      pengajar: { id: 1 },
    };

    $.ajax({
      method: "POST",
      url: `${window.location.origin}/api/spp/create`,
      dataType: "JSON",
      contentType: "application/json",
      beforeSend: addCSRFToken(),
      data: JSON.stringify(sppData),
      success: (res) => {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Data SPP berhasil disimpan!",
          showConfirmButton: false,
          timer: 2000,
        }).then(() => {
          window.location.href = `${window.location.origin}/spp`;
        });
      },
      error: (err) => {
        Swal.fire({
          position: "center",
          icon: "error",
          title: "Terjadi kesalahan saat menyimpan data SPP!",
          showConfirmButton: false,
          timer: 2000,
        });
        console.error("Error:", err);
      },
    });
  }
});

function deleteSpp(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/spp/" + id,
    success: function (data) {
      Swal.fire({
        title: "Apakah Yakin Ingin Menghapus ?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Ya",
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
            method: "Delete",
            url: window.location.origin + "/api/spp/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              Swal.fire("Berhasil! " + " berhasil Dihapus.");
              window.location.reload();
            },
            error: function (err) {
              console.log(err);
            },
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          swal.fire({
            title: "batal",
            text: res.name + " Tidak terhapus",
            icon: "error",
          });
        }
      });
    },
  });
}

$("#edit-spp").on("submit", (event) => {
  event.preventDefault();
  let id = $("#create-spp-id").val();
  let spp = {
    tanggal_bayar: $("#create-bayar").val(),
    jumlah_bayar: 10000,
    bayar_bulan: $("#create-bulan").val(),
    bayar_tahun: $("#create-tahun").val(),
    santri: { id: $("#edit-santri").val() },
    pengajar: { id: 1 },
  };

  console.log(spp);
  $.ajax({
    method: "PUT",
    url: window.location.origin + "/api/spp/" + id,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(spp),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Nilai berhasil diupdate!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/spp";
      });
    },
    error: (err) => {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "Terjadi kesalahan!",
        showConfirmButton: true,
      });
    },
  });
});
