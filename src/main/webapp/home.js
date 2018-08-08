var app = angular.module('clinicalTrialApp', ['ui.router', 'ngMaterial', 'ngAnimate']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider, $window, $rootScope) {

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'home.html'
        })

        .state('searchBar', {
            url: '/searchBar',
            templateUrl: 'searchBar.html',
            controller: 'autoCompleteController'
        })

        .state('recruitingTrials', {
            url: '/recruitingTrialList?condition',
            templateUrl: 'recruitingByCondition.html',
            controller: function($scope, $http, $stateParams) {
                $scope.condition = $stateParams.condition;
                $http.get("http://localhost:8080/clin/clinical/getRecruitingTrialsByCondition/" + $scope.condition).then(function (response) {
                    $scope.trials = response.data;
                });
            }

        })

        .state('allTrials', {
            url: '/fullTrialList?condition',
            templateUrl: 'fullByCondition.html',
            controller: function ($scope, $http, $stateParams) {
                $scope.condition = $stateParams.condition;
                $http.get("http://localhost:8080/clin/clinical/getTrialsByCondition/" + $scope.condition).then(function(response) {
                    $scope.trials = response.data;
                });
            }

        })

        .state('viewTrials', {
            url: '/viewTrials?nct',
            templateUrl: 'trialInfo.html',
            controller: function ($scope, $http, $stateParams) {
                $scope.nct = $stateParams.nct;
                $http.get("http://localhost:8080/clin/clinical/getFullTrialInfo/" + $scope.nct).then(function (response) {
                    $scope.trialInfo = response.data;
                });

                $scope.goBack = function() {
                    $window.history.back();
                };
            }
        })

        .state('findConflicts', {
            url: '/findConflicts?nct',
            templateUrl: 'viewConflicts.html',
            controller: function($scope, $http, $stateParams) {
                $scope.nct = $stateParams.nct;
                $http.get("http://localhost:8080/clin/clinical/getConflicts/" + $scope.nct).then(function (response) {
                    $scope.conflictInfo = response.data;
                });

            }
        })

        .state('viewTrials.descr', {
            templateUrl: 'detailedDescrView.html',
            controller: function ($scope, $http, $stateParams) {
                $scope.nct = $stateParams.nct;
                $http.get("http://localhost:8080/clin/clinical/getFullTrialInfo/" + $scope.nct).then(function (response) {
                    $scope.trialInfo = response.data;
                });
            }
        });


    $urlRouterProvider.otherwise('/searchBar');
}]);


app.controller('autoCompleteController', function($scope) {

    $scope.countryList = [
        "Accelerated Phase Chronic Myelogenous Leukemia", "Acute Leukemia", "Acute Lymphoblastic Leukemia", "Acute Myeloid Leukemia",
        "Acute Myeloid Leukemia (AML)", "Acute Myeloid Lukemia", "Adenaocortical Carcinoma", "Adenocarcinoma", "Adrenocortical Carcinoma",
        "Adult Central Nervous System Germ Cell Tumor", "Adult Renal Wilms Tumor", "Adult Rhabdomyosarcoma", "Adult Synovial Sarcoma", "Advanced Cancers",
        "Alveolar Childhood Rhabdomyosarcoma", "Aniridia", "Astrocytoma", "Atypical Teratoid/Rhabdoid Tumor", "Beckwith-Wiedemann Syndrome",
        "Bladder Cancer", "Blastic Phase Chronic Myelogenous Leukemia", "Bone Cancer", "Brain Neoplasms", "Brain Tumor", "Brain Tumor", " Recurrent",
        "Brain Tumors", "Brain and Central Nervous System Tumors", "Breast Cancer", "Cancer", "Cancer in Children.", "Carcinoma", " Round Cell",
        "Central Nervous System Tumors", "Childhood Acute Lymphoblastic Leukemia", "Childhood Acute Lymphoblastic Leukemia in Remission",
        "Childhood Acute Myeloid Leukemia in Remission", "Childhood Burkitt Lymphoma", "Childhood Central Nervous System Germ Cell Tumor",
        "Childhood Choroid Plexus Tumor", "Childhood Chronic Myelogenous Leukemia", "Childhood Diffuse Large Cell Lymphoma", "Childhood Germ Cell Tumor",
        "Childhood Grade III Lymphomatoid Granulomatosis", "Childhood Hepatoblastoma", "Childhood Hepatocellular Carcinoma",
        "Childhood Immunoblastic Large Cell Lymphoma", "Childhood Liver Cancer", "Childhood Lymphoma", "Childhood Medulloblastoma",
        "Childhood Myelodysplastic Syndromes", "Childhood Pineoblastoma", "Childhood Renal Cell Carcinoma", "Childhood Renal Wilms Tumor",
        "Childhood Rhabdomyosarcoma", "Childhood Soft Tissue Sarcoma", "Childhood Solid Tumor", "Childhood Solid Tumors",
        "Childhood Supratentorial Primitive Neuroectodermal Tumor", "Childhood Synovial Sarcoma", "Chordoma", "Choroid Plexus Neoplasms",
        "Chronic Eosinophilic Leukemia", "Chronic Myeloid Leukemia (CML)", "Chronic Myelomonocytic Leukemia", "Chronic Myeloproliferative Disorders",
        "Chronic Neutrophilic Leukemia", "Chronic Phase Chronic Myelogenous Leukemia", "Clear Cell Carcinoma", "Clear Cell Renal Cell Carcinoma",
        "Clear Cell Sarcoma", "Clear Cell Sarcoma of the Kidney", "Clival Chordoma", "Coldrhood Cancer", "Colorectal Cancer",
        "Congenital Mesoblastic Nephroma", "Craniopharyngioma", "Cystic Nephroma", "Denys-Drash Syndrome", "Desmoplastic Small Round Cell Tumor",
        "Diffuse Hyperplastic Perilobar Nephroblastomatosis", "Disseminated Neuroblastoma", "Dysembryoplastic Neuroepithelial Tumor",
        "Embryonal Childhood Rhabdomyosarcoma", "Embryonal Rhabdomyosarcoma of Cervix", "Ependymoma", "Esophageal Cancer", "Ewing Sarcoma",
        "Ewing Sarcoma of Bone", "Ewing Sarcoma/Peripheral Primitive Neuroectodermal Tumor (PNET)", "Ewing's Sarcoma", "Ewings Sarcoma",
        "Extragonadal Germ Cell Tumor", "Fertility Preservation", "Fibrosarcoma", "Frasier Syndrome", "Full Aniridia", "Gastric Cancer",
        "Gastrointestinal Carcinoid Tumor", "Gastrointestinal Neoplasms","Germ Cell Tumor","Germ Cell Tumors","Gestational Trophoblastic Tumor",
        "Glioblastoma Multiforme of Brain","Glioma","Goiter","Gonadotropin-releasing Hormone Agonist","Graft Versus Host Disease",
        "Graft vs Host Disease","Head and Neck Cancer","Hearing Loss","Hemihypertrophy","Hepatic Tumor","Hepatoblastoma","Hereditary Wilms Tumor",
        "Hodgkin Lymphoma","Hodgkin's Disease","Juvenile Myelomonocytic Leukemia","Kidney Cancer","Kidney Tumor","Leukemia","Leukemia",
        " Lymphoblastic (Acute)","Leukemia"," Myeloid"," Acute","Leukemia"," Myelomonocytic"," Chronic","Liver Cancer","Liver Tumors","Lung Cancer",
        "Lung Metastases","Lymphoma","Lymphomas","Malignant Peripheral Nerve Sheath Tumor","Malignant Rhabdoid Tumor","Medulloblastoma",
        "Medulloblastoma"," Childhood","Medulloepithelioma","Melanoma","Meningioma","Mental Retardation","Metastatic Ewing Sarcoma/Peripheral Primitive Neuroectodermal Tumor",
        "Mucositis","Multiple Myeloma","Multiple Myeloma and Plasma Cell Neoplasm","Mycoses","Myelodysplastic Syndrome","Myelodysplastic Syndromes",
        "Myelodysplastic/Myeloproliferative Neoplasm"," Unclassifiable","Myelodysplastic/Myeloproliferative Neoplasms","Myeloid Leukemia"," Chronic",
        "Nasal Chondromesenchymal Hamartoma","Nasopharyngeal Cancer","Nephroblastoma","Nephrotic Syndrome","Neuroblastoma","Neuroectodermal Tumor",
        "Neuroectodermal Tumors"," Primitive","Neurotoxicity","Neutropenia","Non-CNS Solid Tumors","Non-Hodgkin Lymphoma","Non-rhabdomyosarcoma",
        "Non-rhabdomyosarcoma Soft Tissue Sarcoma","Ocular Medulloepithelioma","Opioid Induced Constipation (OIC)","Oral Complications","Osteosarcoma",
        "Ovarian Cancer","Ovarian Mixed Germ Cell Tumor","Ovarian Neoplasms","Ovarian Sertoli-Leydig Cell Tumors","Pain","Papillary Renal Cell Carcinoma",
        "Papillary Thyroid Cancer","Partial Aniridia","Pathologically Demonstrated Bladder Cancer","Pediatric Solid Tumor","Pediatrics Cancer",
        "Peripheral Neuropathy","Peritoneal Neoplasms","Pineoblastoma","Pituitary Tumors","Plasma Cell Neoplasm","Pleuropulmonary Blastoma",
        "Previously Treated Childhood Rhabdomyosarcoma","Previously Treated Myelodysplastic Syndromes","Previously Untreated Childhood Rhabdomyosarcoma",
        "Primitive Neuroectodermal Tumors (PNETs)","Prostate Cancer","Pulmonary Complications","Recurrent Adrenocortical Carcinoma",
        "Recurrent Adult Brain Tumor","Recurrent Adult Soft Tissue Sarcoma","Recurrent Childhood Acute Lymphoblastic Leukemia",
        "Recurrent Childhood Acute Myeloid Leukemia","Recurrent Childhood Brain Stem Glioma","Recurrent Childhood Cerebellar Astrocytoma",
        "Recurrent Childhood Cerebral Astrocytoma","Recurrent Childhood Ependymoma","Recurrent Childhood Grade III Lymphomatoid Granulomatosis",
        "Recurrent Childhood Large Cell Lymphoma","Recurrent Childhood Liver Cancer","Recurrent Childhood Lymphoblastic Lymphoma",
        "Recurrent Childhood Malignant Germ Cell Tumor","Recurrent Childhood Medulloblastoma","Recurrent Childhood Pineoblastoma",
        "Recurrent Childhood Rhabdomyosarcoma","Recurrent Childhood Small Noncleaved Cell Lymphoma","Recurrent Childhood Soft Tissue Sarcoma",
        "Recurrent Childhood Supratentorial Primitive Neuroectodermal Tumor","Recurrent Childhood Visual Pathway Glioma",
        "Recurrent Childhood Visual Pathway and Hypothalamic Glioma","Recurrent Colon Cancer","Recurrent Ewing Sarcoma/Peripheral Primitive",
        "Recurrent Ewing Sarcoma/Peripheral Primitive Neuroectodermal Tumor","Recurrent Extragonadal Germ Cell Tumor",
        "Recurrent Extragonadal Non-seminomatous Germ Cell Tumor","Recurrent Malignant Peripheral Nerve Sheath Tumor",
        "Recurrent Malignant Testicular Germ Cell Tumor","Recurrent Melanoma","Recurrent Nasopharyngeal Cancer","Recurrent Neuroblastoma",
        "Recurrent Osteosarcoma","Recurrent Ovarian Germ Cell Tumor","Recurrent Retinoblastoma","Recurrent Rhabdomyosarcoma","Recurrent Solid Tumor",
        "Recurrent Synovial Sarcoma","Recurrent Thyroid Cancer","Recurrent Wilms Tumor and Other Childhood Kidney Tumors",
        "Recurrent/Refractory Childhood Hodgkin Lymphoma","Refractory Cancer","Refractory Solid Tumor","Relapsed/Refractory Hematopoietic Malignancies",
        "Relapsing Chronic Myelogenous Leukemia","Renal Cell Carcinoma","Renal Failure","Retinoblastoma","Retroperitoneal Neoplasms","Rhabdoid Tumor",
        "Rhabdoid Tumor of the Kidney","Rhabdomyosarcoma","Sarcoma"," Ewing","Secondary Acute Myeloid Leukemia","Secondary Myelodysplastic Syndromes",
        "Sertoli-Leydig Cell Tumor of Ovary","Soft Tissue Sarcoma","Solid Tumor","Solid Tumors","Stage I Renal Cell Cancer","Stage I Renal Wilms Tumor",
        "Stage I Wilms Tumor","Stage II Renal Cell Cancer","Stage II Renal Wilms Tumor","Stage II Wilms Tumor","Stage III Renal Cell Cancer",
        "Stage III Renal Wilms Tumor","Stage III Wilms Tumor","Stage III Wilms Tumor With Loss of Heterozygosity (LOH) for 1p and 16q",
        "Stage IV Adult Soft Tissue Sarcoma","Stage IV Renal Cell Cancer","Stage IV Renal Wilms Tumor","Stage IV Wilms Tumor","Stage V Renal Wilms Tumor",
        "Stage V Wilms Tumor","Testicular Germ Cell Tumor","Unspecified Adult Solid Tumor"," Protocol Specific","Unspecified Childhood Solid Tumor",
        " Protocol Specific","Urogenital Abnormalities","WAGR Syndrome","Wilm's Tumor","Wilms Tumor","Wilms Tumor and Other Childhood Kidney Tumors",
        "Wilms Tumour","Wilms' Tumor","WilmsTumor","de Novo Myelodysplastic Syndromes "];



    $scope.cname = function(string) {

        $scope.hidethis = false;

        var output = [];

        angular.forEach($scope.countryList, function(country) {

            if (country.toLowerCase().indexOf(string.toLowerCase()) >= 0) {

                output.push(country);

            }

        });

        $scope.filterCountry = output;

    };

    $scope.fillInputBox = function(string) {

        $scope.country = string;

        $scope.hidethis = true;

    };

});

