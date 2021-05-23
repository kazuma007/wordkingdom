/**
 * 
 */
{
        'use strict';
        const word_area     = document.getElementById("word_area");
        const eng_word      = document.getElementById("eng_word").textContent;
        const db_jpn_ans    = document.getElementById("db_jpn_ans").value;
        const btn_to_submit = document.getElementById("btn_to_submit");
        const syns_list = [];
        const def_list = [];
        const url = `https://dictionaryapi.com/api/v3/references/thesaurus/json/${eng_word}?key=522477da-7a4a-4e2d-9dcc-cb6cb35a6554`;

        btn_to_submit.addEventListener("click", () => {
          console.log(eng_word);
          fetch(url)
          .then(response => {
            return response.json();
          })
          .then(data => {
            data[0].meta.syns[0].map(word => {
              syns_list.push(word);
            });
            data[0].shortdef.map(def => {
              def_list.push(def);
            })
            const jpn_ans = document.forms.send_ans.jpn_ans.value;
            if(db_jpn_ans === jpn_ans){
              p_true_ans();
            }else{
//              p_false_ans()
                const p_ans = document.createElement('p');
                p_ans.className = 'false';
                p_ans.textContent = "false";
                word_area.appendChild(p_ans);
                const db_jpn_ans_p = document.createElement('p');
                db_jpn_ans_p.className = 'false_ans';
                db_jpn_ans_p.textContent = db_jpn_ans;
                word_area.appendChild(db_jpn_ans_p);
            }
            deleteTheBtn();
            title("Meaning");
            console.log("Meaning");
            console.log(def_list);
            const def_table = document.createElement('table');
            def_table.className = 'def_table';
            for(let i = 0; i < def_list.length; i++){
              const table_tr = document.createElement('tr');
              const table_td = document.createElement('td');
              table_td.textContent = `${i + 1}. ${def_list[i]}`
              table_tr.appendChild(table_td);
              def_table.appendChild(table_tr);
              console.log(def_list[i]);
            }
            word_area.appendChild(def_table);
//            addMeaning();
            spaceDivBefSyns();
            title("Synonyms");
//            addSyns();
            const syns_p = document.createElement('p');
            for(let i = 0; i < syns_list.length; i++){
              if(i === syns_list.length - 1){
                syns_p.className = 'word_detail_syns';
                syns_p.textContent += `${syns_list[i]}`;
              }else{
                syns_p.className = 'word_detail_syns';
                syns_p.textContent += `${syns_list[i]}, `;
              }
            }
            word_area.appendChild(syns_p);
            addNextBtn();
          })
          .catch(error => {
            console.log('error');
            const jpn_ans = document.forms.send_ans.jpn_ans.value;
            if(db_jpn_ans === jpn_ans){
              p_true_ans();
            }else{
//              p_false_ans()
                const p_ans = document.createElement('p');
                p_ans.className = 'false';
                p_ans.textContent = "false";
                word_area.appendChild(p_ans);
                const db_jpn_ans_p = document.createElement('p');
                db_jpn_ans_p.className = 'false_ans';
                db_jpn_ans_p.textContent = db_jpn_ans;
                word_area.appendChild(db_jpn_ans_p);
            }
            deleteTheBtn();
            addNextBtn();
          });
        })

        function deleteTheBtn(){
          btn_to_submit.style.display = "none";
        }

        function p_true_ans(){
          const p_ans = document.createElement('p');
          p_ans.className = 'true_of_false';
          p_ans.textContent = "true";
          word_area.appendChild(p_ans);
        }

        function p_false_ans(){
            const p_ans = document.createElement('p');
            p_ans.className = 'false';
            p_ans.textContent = "false";
            word_area.appendChild(p_ans);
            const db_jpn_ans_p = document.createElement('p');
            db_jpn_ans_p.className = 'false_ans';
            db_jpn_ans_p.textContent = db_jpn_ans;
            word_area.appendChild(db_jpn_ans_p);
        }

        function title(title){
          const title_pron = document.createElement('p');
          title_pron.className = 'title';
          title_pron.innerHTML = `<i class="far fa-check-square"></i> <u>${title}</u>`;
          word_area.append(title_pron);
        }

        function addMeaning(){
          console.log(def_list);
          const def_table = document.createElement('table');
          def_table.className = 'def_table';
          for(let i = 0; i < def_list.length; i++){
            const table_tr = document.createElement('tr');
            const table_td = document.createElement('td');
            table_td.textContent = `${i + 1}. ${def_list[i]}`
            table_tr.appendChild(table_td);
            def_table.appendChild(table_tr);
            console.log(def_list[i]);
          }
          word_area.appendChild(def_table);
        }
        
        function spaceDivBefSyns(){
		  const div = document.createElement('div');
		  div.className = 'spaceDivBefSyns';
		  word_area.appendChild(div);
		  console.log(div);
		}

        function addSyns(){
          const syns_p = document.createElement('p');
          for(let i = 0; i < syns_list.length; i++){
            if(i === syns_list.length - 1){
              syns_p.className = 'word_detail_syns';
              syns_p.textContent += `${syns_list[i]}`;
            }else{
              syns_p.className = 'word_detail_syns';
              syns_p.textContent += `${syns_list[i]}, `;
            }
          }
          word_area.appendChild(syns_p);
        }

        function addNextBtn(){
          const nextBtn = document.createElement('a');
          nextBtn.setAttribute('href',"/word/nextWord");
          nextBtn.className = 'btn_to_next';
          nextBtn.textContent = 'Next';
          word_area.appendChild(nextBtn);
        }

        function makeSpane(){
          const spaceDiv = document.createElement('div');
          spaceDiv.className = 'spaceDiv';
          word_area.appendChild(spaceDiv);
        }

}