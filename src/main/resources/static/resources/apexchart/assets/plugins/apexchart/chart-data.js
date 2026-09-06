
  Number.prototype.formatMoney = function(c, d, t){
		var n = this,
		    c = isNaN(c = Math.abs(c)) ? 2 : c,
		    d = d == undefined ? "." : d,
		    t = t == undefined ? "," : t,
		    s = n < 0 ? "-" : "",
		    i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c))),
		    j = (j = i.length) > 3 ? j % 3 : 0;
		   return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
		 };

'use strict';



function gent_ca_qte()
	{
	 var options = {
	          series: [
	          {
	            name: "Chiffre d\'affaire",
	            data: vente_ca
	          },
	          {
	            name: "Quantité (Q)",
	            data: vente_qte
	          }
	        ],
	          chart: {
	          height: 250,
	          type: 'line',
	          dropShadow: {
	            enabled: true,
	            color: '#000',
	            top: 18,
	            left: 7,
	            blur: 10,
	            opacity: 0.2
	          },
	          zoom: {
	            enabled: false
	          },
	          toolbar: {
	            show: false
	          }
	        },
	        colors: ['#77B6EA', '#545454'],
	        dataLabels: {
	          enabled: true,
	          formatter: function (val) {
	              return parseFloat(val).formatMoney(2, ',', ' ') 
	            }
	        },
	        stroke: {
	          curve: 'smooth'
	        },
	        title: {
	          text: 'Chiffre d\'affaire ET Quantité par mois',
	          align: 'Center'
	        },
	        grid: {
	          borderColor: '#e7e7e7',
	          row: {
	            colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
	            opacity: 0.5
	          },
	        },
	        markers: {
	          size: 1
	        },
	        xaxis: {
	          categories : mois_vente,
	          title: {
	            text: ''
	          }
	        },
	        yaxis: {
	          title: {
	            text: '',
	         
	          },
	          labels: {
	        	  formatter: function (val) {
		              return parseFloat(val).formatMoney(2, ',', ' ') 
		            }
	              }
	       
	        },
	        legend: {
	          position: 'top',
	          horizontalAlign: 'right',
	          floating: true,
	          offsetY: -25,
	          offsetX: -5
	        },
	        tooltip: {
		          shared: false,
		       
		          y: {
		            formatter: function (val) {
		              return parseFloat(val).formatMoney(2, ',', ' ') 
		            }
		          }
		        },
	        
	        };
$(("#vente_ca_qte")).empty()
	        var chart = new ApexCharts(document.querySelector("#vente_ca_qte"), options);
	        chart.render();	

}




function gent_ca_produit()
{
	 var options = {
	          series: ca_produit,
	          chart: {
	          width: 460,
	          type: 'pie',
	        },
	        title: {
		          text: 'Chiffre d\'affaire par gamme d\'aliment',
		          align: 'Center'
		        },
	        tooltip: {
		          shared: false,
		       
		          y: {
		            formatter: function (val) {
		              return parseFloat(val).formatMoney(2, ',', ' ') 
		            }
		          }
		        },
		        
	        labels: produit_ca,
	        responsive: [{
	          breakpoint: 580,
	          options: {
	            chart: {
	              width: 300
	            },
	            legend: {
	              position: 'bottom'
	            }
	          }
	        }]
	        };
	 $(("#ca_produit")).empty()
	        var chart = new ApexCharts(document.querySelector("#ca_produit"), options);
	        chart.render();
}



function gent_qte_produit()
{
	 var options = {
	          series: qte_produit,
	          chart: {
	          width: 460,
	          type: 'pie',
	        },
	        title: {
		          text: 'Quantité par gamme d\'aliment',
		          align: 'Center'
		        },
	        tooltip: {
		          shared: false,
		       
		          y: {
		            formatter: function (val) {
		              return parseFloat(val).formatMoney(2, ',', ' ') 
		            }
		          }
		        },
		        
	        labels: produit_qte,
	        responsive: [{
	          breakpoint: 580,
	          options: {
	            chart: {
	              width: 300
	            },
	            legend: {
	              position: 'bottom'
	            }
	          }
	        }]
	        };
	 $(("#qte_produit")).empty()
		
	        var chart = new ApexCharts(document.querySelector("#qte_produit"), options);
	        chart.render();
}




function gent_ca_rc()
{
	var options = {
	          series: [{
	          name: 'Servings',
	          data: ca_rc
	        }],
	        tooltip: {
		          shared: false,
		       
		          y: {
		            formatter: function (val) {
		              return parseFloat(val).formatMoney(2, ',', ' ') 
		            }
		          }
		        },
	        chart: {
	          height: 350,
	          type: 'bar',
	          toolbar: {
	              show: false
	            },
	        },
	        title: {
		          text: ' Chiffre d\'affaire par client',
		          align: 'Center'
		        },
	        plotOptions: {
	          bar: {
	        	  borderRadius: 4,
		            borderRadiusApplication: 'end',
		            horizontal: true,
	          }
	        },
	        
	        dataLabels: {
	          enabled: false
	        },
	        stroke: {
	          width: 0
	        },
	        grid: {
	          row: {
	            colors: ['#fff', '#fff']
	          }
	        },
	        xaxis: {
		          categories:rc_ca ,
		          labels: {
		        	  formatter: function (val) {
			              return parseFloat(val).formatMoney(2, ',', ' ') 
			            }
		              }
		        },
	   
	        };
	 $(("#ca_client")).empty()
		
	        var chart = new ApexCharts(document.querySelector("#ca_client"), options);
	        chart.render();
	      
}

function gent_qte_rc()
{
	var options = {
	          series: [{
	          name: 'Servings',
	          data: qte_rc
	        }],
	        tooltip: {
		          shared: false,
		       
		          y: {
		            formatter: function (val) {
		              return parseFloat(val).formatMoney(2, ',', ' ') 
		            }
		          }
		        },
	        chart: {
	          height: 350,
	          type: 'bar',
	          toolbar: {
	              show: false
	            },
	        },
	        title: {
		          text: 'Quantité (Q) par client',
		          align: 'Center'
		        },
	        plotOptions: {
	          bar: {
	        	  borderRadius: 4,
		            borderRadiusApplication: 'end',
		            horizontal: true,
	          }
	        },
	        
	        dataLabels: {
	          enabled: false
	        },
	        stroke: {
	          width: 0
	        },
	        grid: {
	          row: {
	            colors: ['#fff', '#fff']
	          }
	        },
	        xaxis: {
		          categories:rc_qte ,
		          labels: {
		        	  formatter: function (val) {
			              return parseFloat(val).formatMoney(2, ',', ' ') 
			            }
		              }
		        },
	   
	        };
	 $(("#qte_client")).empty()
		
	        var chart = new ApexCharts(document.querySelector("#qte_client"), options);
	        chart.render();
	      
}









