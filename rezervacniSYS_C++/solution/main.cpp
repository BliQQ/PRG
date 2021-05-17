///\author Adam Salich
///\date 16.prosince 2020

/*! \mainpage Uvod
 *
 * \section Přehled
 *
 * Program je urcen pro spravu rezervaci pokoju v hotelu
 * Vstup do programu je proveden ze souboru mistnost.csv,ktery uchovava data o mistnosti jako id,patro,cislo_mistnosti atd...
 * Vstup je dale proveden ze souboru rezervace.csv,který uchovava data jako id_mistnosti a datum_rezervace. Tento soubor je taky využit pro vystup, kdy se do nej ukladaji informace o rezervaci.
 * Program dale zpracovana rezervaci mistnosti na dany den a umoznuje vyhledani pokoje dle data, kapacity a ceny.
 */

/**
 *  @file main.cpp
 *  @brief Hlavní soubor.
 */

#include <iostream>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <fstream>
#include <vector>
using namespace std;
/**
 * @brief Struktura slouzici k uchovavani dat pro mistnosti.
 */
struct Mistnost
{
    string id;  /**< Unikatni cislo pro kazdy pokoj */
    string patro;  /**< Patro,na kterem se pokoj nacházi */
    string cislo_misnosti;  /**< Pocet mistnosti */
    string kapacita_sedadel;  /**< Kapacita pokoje */
    string cena;  /**< Cena pokoje */
};
/**
 * @brief Struktura slouzici k uchovavani dat pro rezervace.
 */
struct Rezervace
{
    string id_mistnosti; /**< Cislo pokoje pro danou rezervaci */
    string den_rezervace; /**< Datum dane rezervace */
};
void vyberAkci(Rezervace *rez,Mistnost *mis, int poc,int poc2);
bool naplnRezervace(ifstream &fromStream, Rezervace *rez,int vel);
bool naplnMistnosti(ifstream &fromStream, Mistnost *mis,int vel);
void Rezerve(string pokoj, string datum,Rezervace *rez,Mistnost *mis,int vel,int vel2);
void vyhledatCena(int c,Mistnost *mis,int vel,Rezervace *rez, int vel2);
void vyhledatKapacita(int c,Mistnost *mis,Rezervace *rez,int vel,int vel2);
void nactiData(string datum,Rezervace *rez,Mistnost *mis,int vel,int vel2);
void vyberAkci(Rezervace *rez,Mistnost *mis, int poc,int poc2);
void vypisAkce();
int pocetRadku(ifstream &soubor);
void vyberAkci(Rezervace *rez,Mistnost *mis, int poc,int poc2);
void vypisHTML(string yy,Rezervace *rez,Mistnost *mis,int lines,int lines2);
bool kontrola(string date, Rezervace *rez,Mistnost *mis,int vel,int vel2);

/**
 * @brief Hlavni funkce main. Volá dalsi funkce a zpracovava dalsi operace.
 *
 * @return 0 jestlize, program probehne uspesne.
 */
int main()
{
    ifstream inR,inM;
    int poc,poc2;
    Rezervace *rez;
    Mistnost *mis;
    inR.open("../vstupnidata/Rezervace.csv");
    inM.open("../vstupnidata/Mistnosti.csv");
    poc = pocetRadku(inR);
    poc2 = pocetRadku(inM);
    rez = new Rezervace[poc];
    mis = new Mistnost[poc2];
    naplnRezervace(inR, rez, poc);
    naplnMistnosti(inM, mis, poc2);
    inR.close();
    inM.close();
    vypisAkce();
    vyberAkci(rez,mis,poc,poc2);
    delete[] rez;
    delete[] mis;
    return 0;
}
 /**
 * @brief Funkce, ktera kontroluje zda bylo datum zadano ve spravnem formatu.
 * @param date Zadane datum uzivatelem
 * @param *rez Pole struktur rezervaci
 * @param *mis Pole struktur mistnosti
 * @param vel pocet zaznamu v souboru
 * @param vel2 pocet zaznamu v souboru
 * @return 1 if the checked month is present in the input file, 0 if not present.
 */
bool kontrola(string date, Rezervace *rez,Mistnost *mis,int vel,int vel2){
        string datum = date;
        if (date.length()!=10){
            return false;
        }
        if ((datum[0]<=51&&datum[0]>=48)&&(datum[1]<=57&&datum[1]>=48)&&
            (datum[2]<='.')&&(datum[3]<=49&&datum[3]>=48)&&(datum[4]<=57&&datum[4]>=48)&&
            (datum[5]>='.')&&(datum[6]<=50&&datum[6]>=49)&&(datum[7]<=53&&datum[7]>=48)&&
            (datum[8]<=57&&datum[8]>=48)&&(datum[9]<=57&&date[9]>=48)){
            if((datum[0]=='0' && datum[1]=='0')||(datum[3]=='0'&&datum[4]=='0')){
                 return false;
            }
            if(datum[0]==51&&datum[1]>49){
                 return false;
            }
            if(datum[3]==49&&datum[4]>50){
                 return false;
            }
             return true;
        }
     return false;
        }

/**
 * @brief Funkce,ktera pro dany soubor spocita pocet radku.
 *
 * @return Pocet radku daneho souboru
 */
int pocetRadku(ifstream &soubor)
    {
    int pocetRadku = 0;
    string poradi;
        while (getline(soubor, poradi))
        {
        pocetRadku++;
        }
    soubor.clear(); //REWIND
    soubor.seekg(0, ios::beg);
    return pocetRadku;
    }
 /**
 * @brief Funkce, ktera naplni strukru Rezervace zaznamy ze souboru
 * @param &fromStream cesta k souboru
 * @param *rez Pole struktur rezervaci
 * @param vel2 pocet zaznamu v souboru
 * @return true, jestlize jsou zaznamy naplneny
 */
bool naplnRezervace(ifstream &fromStream, Rezervace *rez,int vel)
    {
    for(int i = 0; i < vel ; i++)
        {
        getline(fromStream, rez[i].id_mistnosti, ';');
        getline(fromStream, rez[i].den_rezervace);
        }
 return true;
    }
 /**
 * @brief Funkce, ktera naplni strukru Mistnosti zaznamy ze souboru
 * @param &fromStream cesta k souboru
 * @param *mis Pole struktur mistnosti
 * @param vel pocet zaznamu v souboru
 * @return true, jestlize jsou zaznamy naplneny
 */
bool naplnMistnosti(ifstream &fromStream, Mistnost *mis,int vel)
    {
    for(int i = 0; i < vel ; i++){
    getline(fromStream, mis[i].id, ';');
    getline(fromStream, mis[i].patro, ';');
    getline(fromStream, mis[i].cislo_misnosti, ';');
    getline(fromStream, mis[i].kapacita_sedadel, ';');
    getline(fromStream, mis[i].cena);
    }
 return true;
}
 /**
 * @brief Funkce, ktera do souboru vypise rezervaci danou uzivatelem
 * @param pokoj Uzivatelem zadane cislo pokoje
 * @param datum Uzivatelem zadane datum rezervace
 * @param *rez Pole struktur rezervaci
 * @param *mis Pole struktur mistnosti
 * @param vel pocet zaznamu v souboru
 * @param vel2 pocet zaznamu v souboru
 */
void Rezerve(string pokoj, string datum,Rezervace *rez,Mistnost *mis,int vel,int vel2)
{
  ofstream out;
  string m,k;
  string date = datum;
  int pokoy = atoi(pokoj.c_str());
  if(pokoy>30 || pokoy<0){
    cout << "Zadal jsi pokoj,ktery neexistuje, zarezervuj si pokoj od 1 do 30" << endl;
    cin >> pokoy;
  }

  for (int i = 0; i < vel; i++)
    {
        int id = atoi(rez[i].id_mistnosti.c_str());
        if (pokoy == id)
        {
            if(date == rez[i].den_rezervace)
            {
                    system("cls");
                    cout << "Pokoj je v tento den uz zarezervovan, zvol si prosim jiny" << endl;
                    cout << "Zde je seznam volnych pokoju pro vami zadane datum" << endl;
                    nactiData(date,rez,mis,vel,vel2);
                    break;
            }
        }
    }
    out.open("../vstupnidata/Rezervace.csv",ios::app);
    out << pokoy << ";" << date <<endl;
    out.close();
    system("cls");
    cout << "Rezervace uspesne dokoncena" << endl;
    vypisAkce();
    vyberAkci(rez,mis,vel,vel2);
}
 /**
 * @brief Funkce, ktera vyhleda pokoje, do uzivatelem zadane ceny
 * @param c Uzivatelem zadana cena pokoje,do ktere chce vyhledat zaznamy
 * @param *mis Pole struktur mistnosti
 * @param vel pocet zaznamu v souboru
 * @param *rez Pole struktur rezervaci
 * @param ve2 pocet zaznamu v souboru
 */
void vyhledatCena(int c,Mistnost *mis,int vel,Rezervace *rez, int vel2)
{
cout << "Mistnosti do vami urcene ceny" << endl;
cout << "id" << "\t";
cout << "patro" << "\t";
cout << "c_mist"<< "\t";
cout << "posteli" << "\t";
cout << "CENA"<< endl;
    for (int i = 0; i < vel; i++)
        {
        int id = atoi(mis[i].cena.c_str());
            if (c >= id)
            {
            cout << mis[i].id << "\t";
            cout << mis[i].patro << "\t";
            cout << mis[i].cislo_misnosti<< "\t";
            cout << mis[i].kapacita_sedadel << "\t";
            cout << id << endl;
            }
        }
    cout << "Chces si rezervovat jeden z techto pokoju? 1-ANO 2-NE" << endl;
    int tmp;
    cin >> tmp;
    switch(tmp)
        {
    case 1 :
        {
        cout << "Zadej cislo pokoje,ktery chces rezetovaovat a datum ve formatu dd.mm.yyyy" << endl;
        string tmp2,date;
        cin >> tmp2;
        cin >> date;
        system("cls");
        if(kontrola(date,rez,mis,vel,vel2)==true){
            Rezerve(tmp2,date,rez,mis,vel2,vel);
                break;
        }
         else {
               system("cls");
               cout << "Zadali jste datum ve spatnem formatu" << endl;
               vypisAkce();
               vyberAkci(rez,mis,vel,vel2);
               break;
           }
        }
    case 2 :
        {
        }
      system("cls");
      vypisAkce();
      vyberAkci(rez,mis,vel,vel2);
        }
}
 /**
 * @brief Funkce, ktera vyhleda pokoje, do uzivatelem zadane kapacity
 * @param c Uzivatelem zadana kapacita pokoje,do ktere chce vyhledat zaznamy
 * @param *mis Pole struktur mistnosti
 * @param *rez Pole struktur rezervaci
 * @param vel pocet zaznamu v souboru
 * @param vel2 pocet zaznamu v souboru
 */
void vyhledatKapacita(int c,Mistnost *mis,Rezervace *rez,int vel,int vel2)
{
cout << "Mistnosti do vami urcene ceny" << endl;
cout << "id" << "\t";
cout << "patro" << "\t";
cout << "c_mist"<< "\t";
cout << "posteli" << "\t";
cout << "CENA"<< endl;
    for (int i = 0; i < vel; i++)
    {
    int id = atoi(mis[i].kapacita_sedadel.c_str());
      if (c == id)
      {
        cout << mis[i].id << "\t";
        cout << mis[i].patro << "\t";
        cout << mis[i].cislo_misnosti<< "\t";
        cout << mis[i].kapacita_sedadel << "\t";
        cout << mis[i].cena<< endl;
      }
    }
    cout << "Chces si rezervovat jeden z techto pokoju? 1-ANO 2-NE" << endl;
    int tmp;
    cin >> tmp;
    switch(tmp)
        {
    case 1 :
        {
        cout << "Zadej cislo pokoje,ktery chces rezetovaovat a datum ve formatu dd.mm.yyyy" << endl;
        string tmp2,date;
        cin >> tmp2;
        cin >> date;
        system("cls");
        if(kontrola(date,rez,mis,vel,vel2)==true){
            Rezerve(tmp2,date,rez,mis,vel2,vel);
                break;
        }
         else {
               system("cls");
               cout << "Zadali jste datum ve spatnem formatu" << endl;
               vypisAkce();
               vyberAkci(rez,mis,vel,vel2);
               break;
           }
        }
    case 2 :
        {
        }
      system("cls");
      vypisAkce();
      vyberAkci(rez,mis,vel,vel2);
        }
 /**
 * @brief Funkce, ktera vyhleda pokoje, na urcite datum zadane uzivatelem.
 * @param datum Datum pro ktere chce uzivatel najit praznde pokoje
 * @param *rez Pole struktur rezervaci
 * @param *mis Pole struktur mistnosti
 * @param vel pocet zaznamu v souboru
 * @param vel2 pocet zaznamu v souboru
 */
}
void nactiData(string datum,Rezervace *rez,Mistnost *mis,int vel,int vel2)
{
int k=0,z=0,tmp = 0;
int *pole = new int[vel];
string date = datum;

 for(int i = 0;i < vel; i++)
    {
        if (date == rez[i].den_rezervace)
        {
        int id = atoi(rez[i].id_mistnosti.c_str());
        pole[k] = id-1;
        k++;
        }
    }
        for (int c = 0 ; c < k - 1; c++)
        {
        for (int d = 0 ; d < k - c - 1; d++)
            {
                if (pole[d] > pole[d+1])
                {
                tmp  = pole[d];
                pole[d]   = pole[d+1];
                pole[d+1] = tmp;
                }
            }
        }
cout << "Volne mistnosti na vami zvolene datum" << endl;
cout << "id" << "\t";
cout << "patro" << "\t";
cout << "c_mist"<< "\t";
cout << "posteli" << "\t";
cout << "CENA"<< endl;

    for (int j = 0; j < vel2; j++)
    {
    if (j == pole[z])
        {
        z++;
        cout << "Pokoj " << j+1 << " je zarezrervovan." << endl;
        }
    else
        {
    cout << mis[j].id << "\t";
    cout << mis[j].patro << "\t";
    cout << mis[j].cislo_misnosti<< "\t";
    cout << mis[j].kapacita_sedadel << "\t";
    cout << mis[j].cena<< endl;
        }
    }
    cout << "Chces si rezervovat jeden z techto pokoju? 1-ANO 2-NE" << endl;
    int tmp3;
    cin >> tmp3;
    switch(tmp3)
        {
    case 1 :
        {
        cout << "Zadej cislo pokoje,ktery chces rezetovaovat" << endl;
        string tmp2;
        cin >> tmp2;
        system("cls");
        Rezerve(tmp2,date,rez,mis,vel2,vel);
        }
    case 2 :
        {
        }
      system("cls");
      vypisAkce();
      vyberAkci(rez,mis,vel,vel2);
        }
}
 /**
 * @brief Funkce, ktera obstarava vyber z menu.
 * @param *rez Pole struktur rezervaci
 * @param *mis Pole struktur mistnosti
 * @param poc pocet zaznamu v souboru
 * @param poc2 pocet zaznamu v souboru
 */
void vyberAkci(Rezervace *rez,Mistnost *mis, int poc,int poc2){
    int x;
    cin >> x;
    switch(x){
    case 1 :
        {
            cout << "Zadej pokoj,ktery si chces rezervovat a datum ve formatu dd.mm.yyyy" << endl;
            string s,o;
            cin >> o;
            cin >> s;
           if(kontrola(s,rez,mis,poc,poc2)==true){
                Rezerve(o,s,rez,mis,poc,poc2);
           }
           else {
               system("cls");
               cout << "Zadali jste datum ve spatnem formatu" << endl;
               vypisAkce();
               vyberAkci(rez,mis,poc,poc2);
           }
            break;
        }
    case 2 :
        {
            cout << "Zadej datum, pro ktery chces najit volne terminy" << endl;
            string l;
            cin >> l;
            if(kontrola(l,rez,mis,poc,poc2)==true){
                nactiData(l,rez,mis,poc,poc2);
                break;
           }
           else {
               system("cls");
               cout << "Zadali jste datum ve spatnem formatu" << endl;
               vypisAkce();
               vyberAkci(rez,mis,poc,poc2);
               break;
           }
        }
    case 3 :
        {
          cout << "Zadej cenu do které chces vyhledat pokoje" << endl;
          int y;
          cin >> y;
          vyhledatCena(y,mis,poc2,rez,poc);
          break;
        }
    case 4 :
        {
        cout << "Zadej pocet osob pro ktere chces najit pokoj" << endl;
          int p;
          cin >> p;
          vyhledatKapacita(p,mis,rez,poc2,poc);
          break;
        }
    case 5 :
        {
            cout << "Zadej pokoj,ktereho rezervace chces vypsat" << endl;
            string yy;
            cin >> yy;
            vypisHTML(yy,rez,mis,poc,poc2);
            break;
        }
    case 6 :
        {
            exit(0);
        }
        default : {
                cout << "Vyber cislo od 1 do 6" << endl;
                vyberAkci(rez,mis,poc,poc2);
        }
    }
}
 /**
 * @brief Funkce, ktera obstarava vypis do .html
 * @param yy Pole struktur rezervaci
 * @param *rez Pole struktur rezervaci
 * @param *mis Pole struktur mistnosti
 * @param poc pocet zaznamu v souboru
 * @param poc2 pocet zaznamu v souboru
 */
void vypisHTML(string yy,Rezervace *rez,Mistnost *mis,int poc,int poc2){
    ofstream out;
    bool tmp2=true;
    out.open("../vystupnidata/Output.html");
    {
    // HEADER
    out << "<!DOCTYPE HTML PUBLIC \"-/" << "/W3C/" << "/DTD HTML 4.0 Transitional/" << "/EN\"><html><head>"
    << "<meta http-equiv=\"Content-Type\" content=\"text/html" << ";" << " charset=utf-8\">"
    << "<title>Rezervace pokoje</title></head><body>" << endl;
    //BODY
    out << "<p align="
    << "center"
    << "><font size=7><b><i>"
    << "Pokoj č." << yy
    << "</i></b></font></p>"
    << "<p align=\"center\"><font size=5><b>Rezervace tohoto pokoje </b></font></p>"
    << "<p align=\"center\"><table border=\"4\" width=\"400\">"
    << "<tr><th width=\"200\">ID MISTNOSTI</th><th width=\"200\">DEN REZERVACE</th></tr>";

    for ( int i = 0; i < poc; ++i ){
        for ( int j = 1; j <= 2; ++j ){
            string tmp;

            switch(j){
                case 1:
                    {
                    if (yy==rez[i].id_mistnosti)
                        {
                        tmp = rez[i].id_mistnosti;
                        tmp2=true;
                        break;
                        }
                    else tmp2 = false;
                    }
                case 2:
                    {
                    if(tmp2==true){
                    tmp = rez[i].den_rezervace;
                    break;
                    }
                    else tmp2 = false;
                    }
            }
            if(tmp2==true)
                {
                out << "<td>" << tmp << "</td>";
                }
        }
        out << "</tr>";
}
    out << "</table></p>";
    //END
    out << "</body><html>";
    out.close();
    system("cls");
    cout << "Export do HTML proveden uspesne" << endl;
    vypisAkce();
    vyberAkci(rez,mis,poc,poc2);
}
}
 /**
 * @brief Funkce, ktera obstarava vypis menu do console.
 */
void vypisAkce(){
    cout << "Menu: Vyber si jednu z moznosti" << endl;
    cout << "1. Rezervace mistnosti" << endl;
    cout << "2. Vyhledani volneho terminu podle data" << endl;
    cout << "3. Vyhledani volneho terminu podle ceny" << endl;
    cout << "4. Vyhledani volneho terminu podle kapacity pokoje" << endl;
    cout << "5. Export do HTML" << endl;
    cout << "6. Ukoncit" << endl;
}
