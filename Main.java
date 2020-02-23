//Macarie Razvan Cristian, 322CB
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, CloneNotSupportedException
    {
        //readFromFile
        File input = new File("graph.in");
        Scanner cititor = new Scanner(input);
        
        Afisare writer = new Afisare();
        
        int numarDeNoduri = cititor.nextInt();
    
        ArrayList<Muchie> muchii = new ArrayList<>();
        while (true)
        {
            int muchie1 = cititor.nextInt();
            if(muchie1 == -1)
            {
                break;
            }
            int muchie2 = cititor.nextInt();
            muchii.add(new Muchie(muchie1, muchie2));
        }

        //pasul 0, pentru testul 2, daca un nod are gradul 1, se termina tot pentru ca nu putem avea ciclu
        int counter;
        for (int i = 0; i < numarDeNoduri; i++) 
        {
            counter = 0;
            for(Muchie m : muchii)
            {
                if((i+1) == m.destinatie || (i+1) == m.sursa)
                {
                    counter++;
                }
            }
            if(counter == 1)
            {
                 writer.print("x1-1 & ~x1-1");
                 writer.close();
                 System.exit(0); 
            }
        }

        
        //pasul 1, se verifica daca se pleaca pe un singur drum din nodul 1
        ArrayList<Muchie> muchiiCarePleacaDin1 = Muchie.muchiiCarePornescDinX(1, muchii);
       
        writer.print("(");
        for (int i = 0 ; i < muchiiCarePleacaDin1.size() - 1; i++)
        {
            for (int j = i + 1; j < muchiiCarePleacaDin1.size(); j++)
            {
                writer.print("(x" + muchiiCarePleacaDin1.get(i).sursa + "-" + muchiiCarePleacaDin1.get(i).destinatie);
                writer.print("&");
                writer.print("x" + muchiiCarePleacaDin1.get(j).sursa + "-" + muchiiCarePleacaDin1.get(j).destinatie);
                for (int k = 0; k < muchiiCarePleacaDin1.size(); k++)
                {
                    if (k != i && k != j)
                    {
                        writer.print("&");
                        writer.print("~x" + muchiiCarePleacaDin1.get(k).sursa + "-" + muchiiCarePleacaDin1.get(k).destinatie);
                    }
                }
                writer.print(")");
        
                if (i != muchiiCarePleacaDin1.size() - 2)
                {
                    writer.print("|");
                }
            }
        }
        writer.print(")&");
        
        
        //pasul 2, fiecare nod sa aiba exact 2 drumuri care trec prin el
        for (int nod = 1; nod < numarDeNoduri ; nod++)
        {
            ArrayList<Muchie> auxliar;
            auxliar = Muchie.muchiiCareContinX(nod+1, muchii);
            writer.print("(");
            for (int i = 0 ; i < auxliar.size() - 1; i++)
            {
                for (int j = i + 1; j < auxliar.size(); j++)
                {
                    //pentru x
                    if(auxliar.get(i).sursa == nod + 1){
                        writer.print("(x" + auxliar.get(i).sursa + "-" + auxliar.get(i).destinatie);
                    }
                    else {
                        writer.print("(x" + auxliar.get(i).destinatie + "-" + auxliar.get(i).sursa);
                    }
                    writer.print("&");
                    if(auxliar.get(j).sursa == nod + 1)
                    {
                        writer.print("x" + auxliar.get(j).sursa + "-" + auxliar.get(j).destinatie);
                    }
                    else {
                        writer.print("x" + auxliar.get(j).destinatie + "-" + auxliar.get(j).sursa);
                    }
                    for (int k = 0; k < auxliar.size(); k++)
                    {
                        if (k != i && k != j)
                        {
                            writer.print("&");
                            if(auxliar.get(k).sursa == nod + 1)
                            {
                                writer.print("~x" + auxliar.get(k).sursa + "-" + auxliar.get(k).destinatie);
                            }
                            else
                            {
                                writer.print("~x" + auxliar.get(k).destinatie + "-" + auxliar.get(k).sursa);
                            }
                        }
                    }
                    writer.print(")");
                    if(i != auxliar.size() - 2)
                    {
                        writer.print("|");
                    }
                }
            }
            writer.print(")&");
            writer.print("(");
            for (int i = 0; i < numarDeNoduri/2 + 1; i++)
            {
                writer.print("a" + (i+1) + "-" + (nod+1));
                if(i != numarDeNoduri/2)
                {
                    writer.print("|");
                }
            }
            writer.print(")&");
        }
        
        //pasul 3, (x ^ ~y) ^ (~x ^ y) adica ambele sunt 1 sau ambele sunt 0, neorientat
        for (int i = 0; i < muchii.size(); i++)
        {
             writer.print("(");
             
             writer.print("(x" + muchii.get(i).sursa + "-" + muchii.get(i).destinatie);
             writer.print("|");
             writer.print("~x" + muchii.get(i).destinatie + "-" + muchii.get(i).sursa + ")");
             
             writer.print("&");
             
             writer.print("(~x" + muchii.get(i).sursa + "-" + muchii.get(i).destinatie);
             writer.print("|");
             writer.print("x" + muchii.get(i).destinatie + "-" + muchii.get(i).sursa + ")");
             writer.print(")&");
        }
        
        //pasul 4, se trece printr-un singur sens intr-un singur nod
        
        for (int i = 0; i < muchiiCarePleacaDin1.size(); i++)
        {
             writer.print("(");
             
             writer.print("(a" + muchiiCarePleacaDin1.get(i).sursa + "-" + muchiiCarePleacaDin1.get(i).destinatie);
             writer.print("|");
             writer.print("~x" + muchiiCarePleacaDin1.get(i).sursa + "-" + muchiiCarePleacaDin1.get(i).destinatie + ")");
             
             writer.print("&");
             
             writer.print("(~a" + muchiiCarePleacaDin1.get(i).sursa + "-" + muchiiCarePleacaDin1.get(i).destinatie);
             writer.print("|");
             writer.print("x" + muchiiCarePleacaDin1.get(i).sursa + "-" + muchiiCarePleacaDin1.get(i).destinatie + ")");
             writer.print(")&");
        }
        
        //pasul 5, chestii imposibile, cum ar fi a1-1 si a1-k, unde a nu are link cu k
        writer.print("~a1-1&");
        for (int i = 0; i < numarDeNoduri; i++)
        {
            if(!Muchie.existaMuchie(1, i+1, muchii))
            {
                writer.print("~a1-" + (i+1)+"&");
            }
        }
        
        //pasul 6, marele final, aici se verifica exact pe unde am fost
        //pentru fiecare lungime de drum
        for (int lungime = 1; lungime < numarDeNoduri/2 + 1; lungime++)
        {
            //pentru fiecare nod luat ca al doilea din ciclu
            //folosesc muchiiCarePleacaDin1
            for (int nod = 1; nod < numarDeNoduri; nod++)
            {
                writer.print("("); //pentru x
                writer.print("("); //pentru o lungime
                writer.print("a"+(lungime+1)+"-"+(nod+1));
                writer.print("|~");
                ArrayList<Muchie> muchiiCareIlContinPeNod = Muchie.muchiiCareContinXFara1(nod+1, muchii);
                writer.print("("); //pentru toata expresia
                for (int i = 0; i < muchiiCareIlContinPeNod.size(); i++)
                {
                    if(i == 0)
                    writer.print("("); //pentru x
                    if (muchiiCareIlContinPeNod.get(i).destinatie != (nod + 1)){
                        
                        writer.print("(a" + lungime + "-" + (muchiiCareIlContinPeNod.get(i).destinatie));
                        writer.print("&");
                        writer.print("x" + muchiiCareIlContinPeNod.get(i).destinatie + "-" + muchiiCareIlContinPeNod.get(i).sursa + ")");
                    }
                    else
                    {
                        writer.print("(a" + lungime + "-" + (muchiiCareIlContinPeNod.get(i).sursa));
                        writer.print("&");
                        writer.print("x" + muchiiCareIlContinPeNod.get(i).sursa + "-" + muchiiCareIlContinPeNod.get(i).destinatie + ")");
                    }
                    if(i != muchiiCareIlContinPeNod.size() - 1)
                    {
                        writer.print("|");
                    }
                }
                writer.print(")"); //pentru x
                writer.print("&~");
                writer.print("("); //pentru a
                for (int i = 0; i < lungime; i++)
                {
                    writer.print("a"+(i+1)+"-"+(nod+1));
                    if(i != lungime - 1)
                        writer.print("|");
                }
                writer.print(")"); // pentru a
                writer.print(")"); //pentru toata expresia
                if(nod != numarDeNoduri)
                    writer.print(")&");
                else
                {
                     writer.print("))&");
                }
    
                writer.print("(");
                writer.print("~a"+(lungime+1)+"-"+(nod+1));
                writer.print("|");
                writer.print("("); //pentru toata expresia
                //writer.print("("); //pentru x
                for (int i = 0; i < muchiiCareIlContinPeNod.size(); i++)
                {
                     if(i == 0)
                    writer.print("("); //pentru x
                    if (muchiiCareIlContinPeNod.get(i).destinatie != (nod + 1)){
                        
                        writer.print("(a" + lungime + "-" + (muchiiCareIlContinPeNod.get(i).destinatie));
                        writer.print("&");
                        writer.print("x" + muchiiCareIlContinPeNod.get(i).destinatie + "-" + muchiiCareIlContinPeNod.get(i).sursa + ")");
                    }
                    else
                    {
                        writer.print("(a" + lungime + "-" + (muchiiCareIlContinPeNod.get(i).sursa));
                        writer.print("&");
                        writer.print("x" + muchiiCareIlContinPeNod.get(i).sursa + "-" + muchiiCareIlContinPeNod.get(i).destinatie + ")");
                    }
                    if(i != muchiiCareIlContinPeNod.size() - 1)
                    {
                        writer.print("|");
                    }
                }
                writer.print(")"); //pentru x
                writer.print("&~");
                writer.print("("); //pentru a
                for (int i = 0; i < lungime; i++)
                {
                    writer.print("a"+(i+1)+"-"+(nod+1));
                    if(i != lungime - 1)
                        writer.print("|");
                }
                writer.print(")"); // pentru a
                writer.print(")"); //pentru toata expresia
                if(nod != numarDeNoduri - 1)
                    writer.print("))&");
                else
                {
                    writer.print("))");
                }
            }
            if(lungime != numarDeNoduri/2)
            writer.print("&");
        }
        writer.close();
    }
}
