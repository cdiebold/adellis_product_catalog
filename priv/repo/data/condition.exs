alias Adellis.Sales.Condition
conditions = [ 
%Condition{code: "FN", description: "FACTORY NEW"},
%Condition{code: "NE", description: "NEW MATERIAL"},
%Condition{code: "NS", description: "NEW SURPLUS"},
%Condition{code: "OH", description: "OVERHAULED"},
%Condition{code: "SV", description: "SERVICEABLE"},
%Condition{code: "RP", description: "REPAIRABLE"},
%Condition{code: "AR", description: "AS REMOVED"}
]


Enum.map(conditions, fn condition -> Repo.insert!(condition) end)