import React, { useState, useEffect, useMemo } from 'react';
import SwaggerUI from 'libs-hps';
import 'libs-hps/dist/swagger-ui.css';
import '../assets/css/swaggerStyle.css';
import { DownloadIcon } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Badge } from '../components/ui/badge';
import { SimpleTreeView } from '@mui/x-tree-view/SimpleTreeView';
import { TreeItem } from '@mui/x-tree-view/TreeItem';
import { styled } from '@mui/system';
import { Input } from 'antd';
import FolderOpenIcon from '@mui/icons-material/FolderOpen';
import DescriptionIcon from '@mui/icons-material/Description';

const { Search } = Input;

const StyledTreeView = styled(SimpleTreeView)({});

const StyledTreeItem = styled(TreeItem)(({ theme }) => ({
  '.MuiTreeItem-label': {
    display: 'flex',
    letterSpacing: '0.5px',
    fontSize: '12px',
    fontFamily: "ff4",
  },
  '.MuiButton-root': {
    textTransform: 'none',
    justifyContent: 'flex-start',
    width: '100%',
    marginTop: '0px',
    fontSize: '12px',
    borderRadius: '0px',
  },
  '&.selected': {
    color: '#007bff',
  },
}));

const Swagger = () => {
  const [apis, setApis] = useState([]);
  const [selectedApi, setSelectedApi] = useState('https://petstore.swagger.io/v2/swagger.json');
  const [selectedItemId, setSelectedItemId] = useState('overview');
  const [searchValue, setSearchValue] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchApis = async () => {
      try {
        const response = await fetch('http://localhost:8080/sandbox-ui/modules/1/apis');
        const data = await response.json();
        setApis(
          data.map((api) => ({
            id: api.apiId,
            name: api.name,
            link: `${api.link}`,
          }))
        );
        setLoading(false);
      } catch (error) {
        console.error('Error fetching APIs:', error);
        setLoading(false);
      }
    };
    fetchApis();
  }, []);

  const handleApiClick = (apiLink, itemId) => {
    setSelectedApi(apiLink);
    setSelectedItemId(itemId);
    sessionStorage.setItem('selectedApiId', itemId);
    console.log('Selected API Link:', apiLink);
    console.log('Selected API ID:', itemId);
  };

  useEffect(() => {
    if (selectedApi) {
      SwaggerUI({
        url: selectedApi,
        dom_id: '#swagger-ui',
      });
    }
  }, [selectedApi]);




  const filteredApis = useMemo(
    () => apis.filter(api => api.name.toLowerCase().includes(searchValue.toLowerCase())),
    [apis, searchValue]
  );

  return (
    <div className="flex">
      <div className="sidebar">
        <Search
          className='search-bar'
          style={{ paddingInline: 18, marginTop: '44px', borderRadius: '5px', marginBottom: '10px' }}
          placeholder="Search"
          onChange={e => setSearchValue(e.target.value)}
        />
        {loading ? (
          <div>Loading...</div>
        ) : (
          <StyledTreeView defaultExpanded={['overview']}>
            <StyledTreeItem itemId="overview" label={<span><FolderOpenIcon /> API Overview</span>}>
              {filteredApis.length === 0 ? (
                <div style={{ fontSize: '12px', color: 'red' }}>No API with this title</div>
              ) : (
                filteredApis.map((api) => (
                  <StyledTreeItem
                    key={api.id}
                    itemId={api.id}
                    className={selectedItemId === api.id ? 'selected' : ''}
                    label={
                      <Button onClick={() => handleApiClick(api.link, api.id)} className="btn-tree" variant="light">
                        <DescriptionIcon style={{ marginRight: '10px', height: '23px' }} />
                        {api.name}
                      </Button>
                    }
                  />
                ))
              )}
            </StyledTreeItem>
          </StyledTreeView>
        )}
      </div>
      <div className="sectioncenter">
        <div id="swagger-ui" />
      </div>
      {/* <div className="leftSide">
        <h4 className="font-bold" style={{ color: '#32325d' }}>
          API Reference
        </h4>
        <div className="pt-4">
          <ul>
            <li>
              <Badge className="badge">Post</Badge>
            </li>
          </ul>
        </div>
        <div className="pt-4">
          <Button className="btn-sdk" variant="light">
            <DownloadIcon size={16} className="mr-2" /> Client SDKs
          </Button>
        </div> */}
      {/* </div> */}
    </div>
  );
};

export default Swagger;
