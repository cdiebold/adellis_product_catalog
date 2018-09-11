alias Adellis.Catalog.Demilitarization
demils = [ 
%Demilitarization{code: "A", description: "Items subject to the Export Administration Regulations (EAR) in parts 773-74 of Title 15, Code of Federal Regulations (CFR) (CCLI or EAR99) and determined by the DoD to present a low risk when released out of DoD control. No DEMIL, MUT or end use certificate is required. May require an export license from DOC. "},
%Demilitarization{code: "B", description: "USML Items - Multilation (MUT) to the point of scrap required worldwide."},
%Demilitarization{code: "C", description: "USML or CCL Military Items- DEMIL required. Remove  or demilitarize installed key points(s) items as DEMIL code D"},
%Demilitarization{code: "D", description: "USML or CCL Military Items- DEMIL required. Destroy item and components to prevent restoration or repair to a usable condition."},
%Demilitarization{code: "E", description: "DOD DEMIL Program Office  reserves this code for its exclusive - use only. DEMIL instructions must be furnished by the DoD DEMIL Program Office."},
%Demilitarization{code: "F", description: "USML or CCL Military Items- DEMIL required.  Item  managers, equipment specialists, or product specialist must furnish special DEMIL instructions."},
%Demilitarization{code: "G", description: "USML or CCL Military Items- DEMIL required- ammunition and explosives (AE). This code applies to both unclassified and classified AE items."},
%Demilitarization{code: "P", description: "USML Items- DEMIL required. Security Classified Items."},
%Demilitarization{code: "Q", description: "Commerce Control List Items (CCLI) - MUT to the point of scrap required outside the United States. Inside the United States, MUT is required when the DEMIL  Intergrity Code. (IC)  3 and MUT is not required when the DEMIL IS is 6.."}
]


Enum.map(demils, fn demil -> Repo.insert!(demil) end)