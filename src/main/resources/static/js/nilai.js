var role = document.getElementById("role").getAttribute("data-role");
console.log(role);

$(document).ready(function () {
  $("#tabel-nilai").DataTable({
    ajax: {
      method: "GET",
      url: "api/nilai/santri",
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
});

function navigateAndFetch(id) {
  window.location.href = `/nilai/detail?id=${id}`;
}

function getById(id) {
  console.log("Fetching details for ID:", id);

  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/nilai/santri/" + id,
    success: function (data) {
      console.log("Fetched data:", data);

      $("#nama").text(data[0].santri.name);
      $("#jilid").text(data[0].santri.jilid.name);

      $("#tabel-detail-nilai").DataTable({
        data: data,
        columns: [
          {
            data: null,
            render: (data, type, row, meta) => {
              return meta.row + 1;
            },
          },
          {
            data: "al_harakat",
          },
          {
            data: "makhraj",
          },
          {
            data: "imla",
          },
          {
            data: "an_namroh",
          },
          {
            data: "al_lafadz",
          },
          {
            data: "tajwid",
          },
          {
            data: "gharib",
          },
          {
            data: "al_mad",
          },
          {
            data: "tanggal_ujian",
          },
          {
            data: null,
            render: (data) => {
              if (role == "[ROLE_PENGAJAR]") {
                return `
                <div class="d-flex gap-3 justify-content-center">
                  <button type="button" class="btn btn-warning" onclick="navigateAndFetch(${data.santri.id})">
                    Update
                  </button>
                   </button>
                   <button type="button" class="btn btn-danger" onclick="navigateAndFetch(${data.santri.id})">
                    Hapus
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
  const santriId = urlParams.get("id");

  if (santriId) {
    getById(santriId);
  } else {
    console.error("ID Santri tidak ditemukan di URL");
  }
});
