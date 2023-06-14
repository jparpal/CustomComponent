# CustomComponent
Component de Captura d’Escriptura: Manual d’Usuari
Introducció
La component de captura d’escriptura és una eina que permet als usuaris capturar i escriure en un canvas dins de les seves aplicacions Android. Aquest manual servirà d’ajuda en el procés d’integració de la llibreria en el vostre projecte i proporcionarà instruccions sobre el seu ús.
Instal·lació
Per utilitzar La component de captura d’escriptura en la vostra aplicació Android, seguiu aquests passos:
1.	Importeu el fitxer ‘.aar’ corresponent a la llibreria en la carpeta ‘libs’ normalment situada en el directori ‘../nom_applicació/app/libs’ .
2.	Obriu el fitxer ‘build.gradle’ del vostre projecte.
3.	Afegiu la següent dependència:

 dependencies {
    implementation files('libs/componentCapturaEscriptura-debug.aar')
}

4.	Sincronitzeu el projecte per assegurar-vos que la llibreria s’afegeix correctament.

Integració
Opció 1: Utilitzant la classe ‘CustomComponent’
1.	Al vostre fitxer XML ‘layout’, afegiu el ‘CustomComponent’ de la següent manera:

 ![image](https://github.com/jparpal/CustomComponent/assets/64012369/5695c87f-9efd-4daf-baca-9e0bc7f30719)


2.	Aquesta component la podeu tractar com a un ‘constraint layout’ i aplicar-hi els canvis necessaris per al vostre programa.
Aquest és un exemple creat en Android Studio que utilitza la component en el fitxer ‘activity_main.xml’ de la aplicació i modifica els marges del layout de la component, la situa sota una text view i ajusta les seves dimensions:

 

3.	Ala vostra activitat o fragment, feu referència al ‘CustomComponent’ i personalitzeu-ne l’aparença si ho considereu necessari:

 

Opció 2: Creació d’un disseny personalitzat
1.	Al vostre fitxer XML layout, creeu un disseny personalitzat amb el DrawingView i dos botons:

 



2.	A la vostra activitat o fragment, feu referència al DrawingView i personalitzeu-ne la funcionalitat:

 

Mètodes Disponibles
A continuació, es mostren els mètodes disponibles per a l’ús de la component de captura d’escriptura:
Mètodes de la classe CustomComponent
‘setButtonSize(int width, int height)’
•	Descripció: Estableix la mida dels botons del CustomComponent.
•	Paràmetres:
•	‘width’: L’amplada desitjada dels botons, en píxels.
•	‘height: L’altura desitjada dels botons, en píxels.

‘setComponentBackgroundColor(int color)’
•	Descripció: Estableix el color de fons de l’espai d’escriptura de la component.
•	Paràmetres:
•	‘color: El color de fons desitjat, representat com a valor enter.

‘setInkColor(int color)’
•	Descripció: Estableix el color de la tinta utilitzada al canvas.
•	Paràmetres:
•	‘color: El color de tinta desitjat, representat com a valor enter.

Mètodes de la classe DrawingView
‘clearCanvas()’
•	Descripció: Esborra tot el contingut del canvas.

‘saveDrawing()’
•	Descripció: Desa les dades obtingudes del dibuix, coordenades, pressió, timestamp i angle en un fitxer de text en el directori ‘DOCUMENTS’ del dispositiu. El nom del fitxer l’escull l’usuari.

‘setInkColor(int color)’
•	Descripció: Estableix el color de la tinta utilitzada al canvas.
•	Paràmetres:
•	‘color: El color de tinta desitjat, representat com a valor enter.

‘setCanvasBackgroundColor(int color)’
•	Descripció: Estableix el color de fons del canvas.
•	Paràmetres:
•	‘color: El color de fons desitjat, representat com a valor enter.

Resolució de problemes
Si trobeu problemes o errors  mentre utilitzeu la llibreria, seguiu aquests passos:
•	Verifiqueu que heu afegit la llibreria correctament com a dependència al vostre projecte.
•	Assegureu-vos de tenir els permisos necessaris per escriure a l’emmagatzematge quan deseu les dades.
•	Comproveu si hi ha algun missatge d’error a la sortida del logcat i cerqueu solucions en línia.

Bones Pràctiques
•	Seguiu aquestes bones pràctiques per aprofitar el màxim la llibreria.:
•	Intentar evitar els dibuixos excessivament grans o complexos, ja que poden afectar el rendiment.
•	Utilitzeu colors de tinta que tinguin un bon contrast amb el color de fons del canvas.

Conclusió
Heu integrat i utilitzat amb èxit la llibreria component de captura d’escriptura a la vostra aplicació Android. Seguint les indicacions  i les bones pràctiques descrites en aquest manual, estaràs preparat per integrar la llibreria a la teva aplicació i aprofitar les seves funcionalitat.


















Exemples
A continuació trobareu el codi dels dos exemples que s’han utilitzat en aquest manual i que estan penjats a la plataforma GitHub.
Aquest respositori, https://github.com/jparpal/Custom-Component-exemple, correspon al projecte on s’utilitza la classe ‘Custom-Component’, que genera una aplicació com la que veiem a la imatge de a continuació:

 
Captura del programa ‘custom component exemple’, amb l’emulador d’Android Studio


I aquest, https://github.com/jparpal/drawing-view-exemple, correspon al projecte que utilitza la classe ‘DrawingView’.

 
Captura del programa ‘drawing view exemple’, amb l’emulador d’Android Studio

